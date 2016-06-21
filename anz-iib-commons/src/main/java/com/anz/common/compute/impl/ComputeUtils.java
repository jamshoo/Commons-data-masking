/**
 * 
 */
package com.anz.common.compute.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;

import com.anz.common.cache.impl.CacheHandlerFactory;
import com.anz.common.compute.ComputeInfo;
import com.anz.common.transform.TransformUtils;
import com.ibm.broker.config.proxy.ApplicationProxy;
import com.ibm.broker.config.proxy.BrokerProxy;
import com.ibm.broker.config.proxy.ConfigManagerProxyLoggedException;
import com.ibm.broker.config.proxy.ConfigManagerProxyPropertyNotInitializedException;
import com.ibm.broker.config.proxy.ConfigurableService;
import com.ibm.broker.config.proxy.ExecutionGroupProxy;
import com.ibm.broker.config.proxy.MessageFlowProxy;
import com.ibm.broker.plugin.MbBLOB;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbJSON;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;

/**
 * @author sanketsw & psamon
 * 
 */
public class ComputeUtils {
	
	private static final int MSG_ID_LENGTH = 24;
	private static final int INIT_TO_ZERO = 0;
	protected static Logger logger = LogManager.getLogger();

	/**
	 * Converts JSON string to a message tree and assign to outMessage
	 * 
	 * @param outMessage
	 *            outMessage
	 * @param outputJson
	 *            JSON string
	 * @throws Exception
	 */
	public static void replaceJsonData(MbMessage outMessage, String outputJson)
			throws Exception {
		MbElement outMsgRootEl = outMessage.getRootElement();
		String parserName = MbJSON.PARSER_NAME;
		String messageType = "";
		String messageSet = "";
		String messageFormat = "";
		int encoding = 0;
		int ccsid = 0;
		int options = 0;
		outMsgRootEl.getFirstElementByPath("JSON").detach();
		outMsgRootEl.createElementAsLastChildFromBitstream(
				outputJson.getBytes("UTF-8"), parserName, messageType,
				messageSet, messageFormat, encoding, ccsid, options);

	}

	/**
	 * Converts a string to a message tree and assign to outMessage
	 * 
	 * @param outMessage
	 *            outMessage
	 * @param outputJson
	 *            a string
	 * @throws Exception
	 */
	public static void replaceStringAsBlob(MbMessage outMessage,
			String outputJson) throws Exception {
		MbElement outMsgRootEl = outMessage.getRootElement();
		String parserName = MbBLOB.PARSER_NAME;
		String messageType = "";
		String messageSet = "";
		String messageFormat = "";
		int encoding = 0;
		int ccsid = 0;
		int options = 0;

		MbElement blob = outMsgRootEl.getFirstElementByPath("/BLOB/BLOB");
		if (blob != null) {
			blob.detach();
		}
		blob = outMsgRootEl.getFirstElementByPath("BLOB");
		if (blob != null) {
			blob.detach();
		}
		outMsgRootEl.createElementAsLastChildFromBitstream(
				outputJson.getBytes(), parserName, messageType, messageSet,
				messageFormat, encoding, ccsid, options);

	}

	/**
	 * Get the JSON Data from the message
	 * 
	 * @param inMessage
	 *            Message
	 * @return JSON String
	 * @throws Exception
	 */
	public static String getJsonData(MbMessage inMessage) throws Exception {
		MbElement jsonElem = inMessage.getRootElement().getFirstElementByPath(
				"JSON/Data");
		if (jsonElem == null)
			return null;
		byte[] bs = jsonElem.toBitstream(null, null, null, 0, 1208, 0);
		if (bs == null)
			return null;
		String inputJson = new String(bs, "UTF-8");
		return inputJson;
	}

	/**
	 * Get the String from the BLOB in the message
	 * 
	 * @param inMessage
	 *            Message
	 * @return String
	 * @throws Exception
	 */
	public static String getStringFromBlob(MbMessage inMessage)
			throws Exception {
		MbElement blobElem = inMessage.getRootElement().getFirstElementByPath(
				"BLOB/BLOB");
		if (blobElem == null) {
			blobElem = inMessage.getRootElement().getFirstElementByPath("BLOB");
		}
		if (blobElem == null)
			return null;
		byte[] bs = (byte[]) blobElem.getValue();

		if (bs == null)
			return null;
		String inputStr = new String(bs);
		return inputStr;
	}

	/**
	 * Get the short exception text from the Exceptions Tree of the message
	 * @param outAssembly
	 * @return short exception text string delimited by :
	 * @throws Exception 
	 */
	public static String getExceptionText(MbMessageAssembly outAssembly) throws Exception {
		// This is internal failure in transformation or logic
		MbElement exception = outAssembly.getExceptionList().getRootElement().getFirstChild();
		String exceptionText = null;
		if(exception != null && exception.getFirstChild() != null) {
			exceptionText = "Error";
			exceptionText = formExceptionText(exception, exceptionText);
		}		
		return exceptionText;
	}

	private static String formExceptionText(MbElement self, String exceptionText) throws Exception {
		String text = self.getValueAsString();
		if (text != null && !text.isEmpty()) {
			exceptionText = exceptionText + ": " + self.getName() + " " + text;
		}
		MbElement child = self.getFirstChild();
		while(child != null) {
			exceptionText = formExceptionText(child, exceptionText);
			child = child.getNextSibling();
		}
		return exceptionText;
		
	}

	/**
	 * Set the reply status of the http response header.
	 * @param outAssembly
	 * @param statusCode e.g. 500
	 * @return the replystatuscode element Destination/HTTP/ReplyStatusCode
	 * @throws MbException
	 */
	public static MbElement setHttpReplyStatus(MbMessageAssembly outAssembly, String statusCode) throws MbException {
		// Create an HTTPResponseHeader with Error value to return for the
		// failure if it is missing
		MbElement localEnvironment = outAssembly.getLocalEnvironment().getRootElement();
		
		MbElement destination = localEnvironment.getFirstElementByPath("Destination");
		destination = destination == null ? 
				localEnvironment.createElementAsLastChild(MbElement.TYPE_NAME, "Destination",null) : 
					destination;
		
		MbElement http = destination.getFirstElementByPath("HTTP");
		http = http == null ? 
				destination.createElementAsLastChild(MbElement.TYPE_NAME, "HTTP", null) : 
					http;
		
		MbElement replyStatusCode = http.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "ReplyStatusCode", statusCode);
		return replyStatusCode;
	}

	/**
	 * Insert a new element if does not exist or change value of existing element
	 * @param value to be set to element
	 * @param message LocalEnvironment or Message
	 * @param path an array of strings of the elements in the path e.g. "Destination", "HTTP", "RequestIdentifier"
	 * @return the element with its value set to new value
	 * @throws MbException
	 */
	public static MbElement setElementInTree(Object value, MbMessage message, String... path ) throws MbException {
		MbElement prevElement = message.getRootElement();		
		
		for(int i=0; i < path.length -1; i++ ) {
			String elementKey = path[i];
			MbElement element = prevElement.getFirstElementByPath(elementKey);
			element = element == null ? 
					prevElement.createElementAsFirstChild(MbElement.TYPE_NAME, elementKey,null) : 
						element;
			prevElement = element;
		}
		String lastElementKey = path[path.length -1];	
		MbElement lastElement = prevElement.getFirstElementByPath(lastElementKey);
		if(lastElement == null) {
			lastElement = prevElement.createElementAsFirstChild(MbElement.TYPE_NAME_VALUE, lastElementKey, value);
		} else {
			lastElement.setValue(value);
		}
		return lastElement;
	}
	
	/**
	 * Remove the specified element from the message subtree
	 * @param message
	 * @param elementPath
	 * @throws Exception
	 */
	public static void removeElementFromTree(MbMessage message, String elementPath) throws Exception {
		// Detach Original Exception Node from the response
		MbElement element = message.getRootElement().getFirstElementByPath(elementPath);
		if(element != null)
			element.detach();
		
	}
	
	/**
	 * Remove message body from the message subtree
	 * @param outMessage
	 * @throws Exception
	 */
	public static void removeMessageBody(MbMessage outMessage) throws Exception {
		// Detach Original Message Body Node from the response
		MbElement messageBody = outMessage.getRootElement().getLastChild();
		if(messageBody != null)
			messageBody.detach();
		
	}


	/**
	 * Get the varible defined from user defined configuratino service named nodeProperties
	 * @return
	 * @throws Exception
	 */
	public static Properties getGlobalVariables() throws Exception {
		Properties props = null;
		String value = CacheHandlerFactory.getInstance().lookupCache("UserDefinedPropetiesCache", "nodeProperties");
		logger.debug(value);
		if(value == null) { 
			BrokerProxy b = BrokerProxy.getLocalInstance();
			while(!b.hasBeenPopulatedByBroker()) { Thread.sleep(100); } 
			ConfigurableService myUDCS = b.getConfigurableService("UserDefined", "nodeProperties");
			props = myUDCS.getProperties();
			if(props != null) {
				CacheHandlerFactory.getInstance().updateCache("UserDefinedPropetiesCache", "nodeProperties", TransformUtils.toJSON(props));
			}
			logger.info(TransformUtils.toJSON(props));
		} else {
			props = TransformUtils.fromJSON(value, Properties.class);
		}		
		return props;
	}

	/**
	 * Get Transaction id from the predefined paths
	 * @param inAssembly
	 * @return transaction id element
	 * @throws MbException
	 */
	public static String getTransactionId(MbMessageAssembly inAssembly) {
		
		String[] paths = {"/HTTPInputHeader/Transaction-Id", "/MQMD/MsgId" };
		String transactionId = null;
		String requestIdentifier = null;
		
		try {
			MbElement messageRoot = inAssembly.getMessage().getRootElement();
			MbElement requestIdentifierElem = inAssembly.getLocalEnvironment().getRootElement().getFirstElementByPath("/Destination/HTTP/RequestIdentifier");
			if(requestIdentifierElem != null) {
				byte[] bytes = (byte[]) requestIdentifierElem.getValue();
				requestIdentifier = new String(bytes);
			} else {
				requestIdentifierElem = messageRoot.getFirstElementByPath("/MQMD/CorrelId");
				requestIdentifier = requestIdentifierElem != null? requestIdentifierElem.getValueAsString(): requestIdentifier;
			}
		} catch(Exception e) { logger.throwing(e); }
		
		if(requestIdentifier != null) {
			transactionId = CacheHandlerFactory.getInstance().lookupCache(CacheHandlerFactory.TransactionIdCache, requestIdentifier);
			logger.debug("Transaction Id found in cache");
			if(transactionId != null) {
				return transactionId;
			}
		}
		
		try {
			MbElement messageRoot = inAssembly.getMessage().getRootElement();
			for(String path: paths) {
				logger.debug("Looking for id in {}", path);
				MbElement transactionIdElem = messageRoot.getFirstElementByPath(path);
				if(transactionIdElem != null) {	
					transactionId = transactionIdElem.getValueAsString();
					logger.info("Transaction Id found in {}", path);
					break;
				}
			}
		} catch(Exception e) { logger.throwing(e); }
		
		if(requestIdentifier == null) {
			logger.info("requestIdentifier/CorrelId not found to update transaction id in the cache");
			return transactionId;
		} else if(transactionId != null) {
			CacheHandlerFactory.getInstance().updateCache(CacheHandlerFactory.TransactionIdCache, requestIdentifier, transactionId);
		}
		
		if(transactionId == null) {
			logger.info("Transaction Id NOT found in any assigned paths");
		} 
		return transactionId;
		
	}
	
	
	/**
	 * Get MessageFlowProxy object for current flow
	 * @param brokerName
	 * @param serverName
	 * @param appName
	 * @param flowName
	 * @return MessageFlowProxy
	 */
	public static MessageFlowProxy getFlowProxy(String brokerName, String serverName, String appName, String flowName) {
			
			
			BrokerProxy broker;
			
			try {
				
				broker = BrokerProxy.getLocalInstance(brokerName);	
				
				logger.info("broker = {}", broker);
				ExecutionGroupProxy server = broker.getExecutionGroupByName(serverName);
				
				ApplicationProxy app = server.getApplicationByName(appName);
				
				logger.info("server = {}", server);
				MessageFlowProxy flow = app.getMessageFlowByName(flowName);
				
				logger.info("flow = {}", flow);
				
				return flow;
					
				
			} catch (Exception e) {
				
				logger.throwing(e);
				
			}

			return null;

		
	}
	
	
	/**
	 * Convert String to MsgId format
	 * @param id
	 * @return MsgId as byte array
	 */
	public static byte[] getMsgIdFromString(String id) {
		
		logger.info("getMsgIdFromString:");
		
		byte[] idByteArray = id.getBytes();
		byte[] msgIdByteArray = new byte[MSG_ID_LENGTH];
		int difference = MSG_ID_LENGTH - idByteArray.length;	
		
		if(difference < 0) {
			
			logger.error("ID too long");
			
			return null;
			
		}	

		int idIndex = INIT_TO_ZERO;
			
		for(int msgIndex = INIT_TO_ZERO; msgIndex < MSG_ID_LENGTH; msgIndex++){
		
			if(msgIndex < difference){
					
				msgIdByteArray[msgIndex] = 0;
					
			} else {
					
				msgIdByteArray[msgIndex] = idByteArray[idIndex++];
					
			}
				
		}
		
		logger.info("msgIdByteArray = {}", msgIdByteArray);
		return msgIdByteArray;
		
	}
	
	/**
	 * Convert json array string to List
	 * @param json
	 * @return List<String>
	 */
	public static List<String> jsonStringToArray(String json) throws JSONException {

	    List<String> stringArray = new ArrayList<String>();

	    JSONArray jsonArray = new JSONArray(json);

	    for (int i = 0; i < jsonArray.length(); i++) {
	        stringArray.add(jsonArray.getString(i));
	    }

	    return stringArray;
	}
	
}
