/**
 * 
 */
package com.anz.common.error;

import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anz.common.compute.ComputeInfo;
import com.anz.common.compute.LoggingTerminal;
import com.anz.common.compute.impl.ComputeUtils;
import com.anz.common.dataaccess.models.iib.ErrorStatusCode;
import com.anz.common.domain.ErrorStatusCodeDomain;
import com.anz.common.transform.ITransformer;
import com.anz.common.transform.TransformUtils;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;

/**
 * @author sanketsw
 * 
 */
public class TransformFailureResponse implements
		ITransformer<MbMessageAssembly, String> {
			
	private static final Logger logger = LogManager.getLogger();

	public String execute(MbMessageAssembly outAssembly, Logger appLogger,
			ComputeInfo metadata) throws Exception {
		logger.entry();
		
		String out = null;

		String exceptionText = ComputeUtils.getExceptionText(outAssembly);
		logger.info("exceptionText {} ", exceptionText);

		MbMessage outMessage = outAssembly.getMessage();
		String messageString = ComputeUtils.getStringFromBlob(outMessage);

		// Log the input blob
		logger.info("messageString {} ", messageString);
		
		// This could be an HTTP Request exception. It is returned back in input message
		if(exceptionText == null) {
			exceptionText = messageString;
		}
		
		String errorString = null;
		if((exceptionText == null && messageString== null) || (exceptionText.toLowerCase().contains("timeout"))) {
			// This is a timeout on MQ
			errorString = ErrorStatusCode.TimeoutException;
		} else {	
			errorString = ErrorStatusCode.InternalException;
		}

		ExceptionMessage exceptionMessage = new ExceptionMessage();
		exceptionMessage.setId(metadata.getMessageId());
		exceptionMessage.setTerminal(LoggingTerminal.OUTPUT.getValue());
		exceptionMessage.setIncidentArea(metadata.getUserDefinedProperties().get("IncidentArea"));
		exceptionMessage.setTimestamp(Calendar.getInstance().getTime());
		exceptionMessage.setMessage(messageString);
		exceptionMessage.setBrokerAndServiceDetails(metadata);
		exceptionMessage.setStaticProperties();	
		
		// Return the error after mapping errorCode from cache/database
		ErrorStatusCode errorCode = ErrorStatusCodeDomain.getInstance().getErrorCode(errorString);
				
		// If error code cannot be mapped, then return the original error
		if (errorCode != null) {
			exceptionMessage.setStatus(errorCode);
			exceptionMessage.getStatus().setDescr(exceptionText);
		} else {
			exceptionMessage.setShortException(exceptionText);
		}
		
		out = TransformUtils.toJSON(exceptionMessage);
		logger.error(out);
		return out;
	}


}
