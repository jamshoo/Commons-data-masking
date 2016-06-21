package com.anz.common.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.ibm.broker.config.proxy.AttributeConstants;
import com.ibm.broker.config.proxy.BrokerProxy;
import com.ibm.broker.config.proxy.Checkpoint;
import com.ibm.broker.config.proxy.ConfigManagerProxyLoggedException;
import com.ibm.broker.config.proxy.ConfigManagerProxyPropertyNotInitializedException;
import com.ibm.broker.config.proxy.ExecutionGroupProxy;
import com.ibm.broker.config.proxy.MessageFlowProxy;
import com.ibm.broker.config.proxy.RecordedTestData;

public abstract class FlowTest {

	private static final Logger logger = LogManager.getLogger();

	// FIXME make this configurable from system property
	private static final String BROKER_NODE_NAME = "TESTNODE_root";
	private static final String INTEGRATION_SERVER_NAME = "default";

	private static BrokerProxy brokerNodeProxy;
	private static ExecutionGroupProxy integrationServerProxy;
	private MessageFlowProxy flowProxy;
	
	@BeforeClass
	public static void initialise() throws ConfigManagerProxyLoggedException,
			ConfigManagerProxyPropertyNotInitializedException, InterruptedException {
		// get broker
		brokerNodeProxy = BrokerProxy.getLocalInstance(BROKER_NODE_NAME);

		if (brokerNodeProxy != null) {
			if (!brokerNodeProxy.isRunning()) {
				// stop test execution
				fail("Broker Node "
						+ BROKER_NODE_NAME
						+ " is not running. Please start the Node before running Tests.");
			} else {
				// setup integration server reference
				integrationServerProxy = brokerNodeProxy
						.getExecutionGroupByName(INTEGRATION_SERVER_NAME);

				if (integrationServerProxy != null) {
					// start integration server
					if (!integrationServerProxy.isRunning()) {
						integrationServerProxy.start();

						// enable test injection mode
						integrationServerProxy
								.setInjectionMode(AttributeConstants.MODE_ENABLED);
						integrationServerProxy
								.setTestRecordMode(AttributeConstants.MODE_ENABLED);
						
						while(!integrationServerProxy.hasBeenPopulatedByBroker()) { Thread.sleep(100); } 						

					}
				} else {
					fail("Integration Server "
							+ INTEGRATION_SERVER_NAME
							+ " is not configured in Broker Node "
							+ BROKER_NODE_NAME
							+ ". Please configure the Integrat before running Tests.");
				}
			}
		}
	}

	@AfterClass
	public static void finalise()
			throws ConfigManagerProxyPropertyNotInitializedException,
			ConfigManagerProxyLoggedException {
		integrationServerProxy.clearRecordedTestData();
		integrationServerProxy
				.setInjectionMode(AttributeConstants.MODE_DISABLED);
		integrationServerProxy
				.setTestRecordMode(AttributeConstants.MODE_DISABLED);

	}

	@Before
	public void setup()
			throws ConfigManagerProxyPropertyNotInitializedException,
			ConfigManagerProxyLoggedException, IOException {
		integrationServerProxy.clearRecordedTestData();
		integrationServerProxy
				.setInjectionMode(AttributeConstants.MODE_DISABLED);
		integrationServerProxy
				.setTestRecordMode(AttributeConstants.MODE_DISABLED);

		// enable test injection mode
		integrationServerProxy
				.setInjectionMode(AttributeConstants.MODE_ENABLED);
		integrationServerProxy
				.setTestRecordMode(AttributeConstants.MODE_ENABLED);
		try {
			while(!integrationServerProxy.hasBeenPopulatedByBroker()) { Thread.sleep(100); } 
		} catch(Exception e) {
			logger.throwing(e);
		}
	}

	public static BrokerProxy getBrokerNode() {
		return brokerNodeProxy;
	}

	public static ExecutionGroupProxy getIntegrationServerProxy() {
		return integrationServerProxy;
	}
	
	

	/**
	 * @return the flowProxy
	 */
	public MessageFlowProxy getFlowProxy() {
		return flowProxy;
	}

	/**
	 * @param flowProxy the flowProxy to set
	 */
	public void setFlowProxy(MessageFlowProxy flowProxy) {
		this.flowProxy = flowProxy;
	}

	public Node getNodeOutput(RecordedTestData recordedTestData, String node)
			throws XPathExpressionException, SAXException, IOException,
			ParserConfigurationException {
		String outMessage = recordedTestData.getTestData().getMessage();
		logger.debug(outMessage);

		InputStream stream = new ByteArrayInputStream(outMessage.getBytes());

		// get the node such as BLOB/BLOB node from the Message Tree
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();

		Document doc = db.parse(stream);
		// doc.getDocumentElement().normalize();

		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		XPathExpression expr = xpath.compile(node);

		NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

		assertNotNull(nl);
		assertEquals(1, nl.getLength());
		logger.info("{} = {}", node, nl.item(0).getTextContent());
		return nl.item(0);
	}

	public String getNodeOutputJsonStringFromBlob(
			RecordedTestData recordedTestData) throws XPathExpressionException,
			SAXException, IOException, ParserConfigurationException {
		Node n = getNodeOutput(recordedTestData, "/message/BLOB/BLOB");
		assertNotNull(n);
		@SuppressWarnings("restriction")
		String json = new String(DatatypeConverter.parseHexBinary(n
				.getTextContent()));
		logger.info(json);
		return json;
	}

	public List<RecordedTestData> getTestDataList(String nodeName)
			throws ConfigManagerProxyPropertyNotInitializedException {
		// get test data for verification
		Properties filterProps = new Properties();

		// Get NodeUUID from nodeName
		String nodeUUID = getNodeUUID(nodeName);
		logger.info("Testing Node={} UUID={} ", nodeName, nodeUUID);

		filterProps.put(Checkpoint.PROPERTY_SOURCE_NODE_NAME, nodeUUID);
		List<RecordedTestData> dataList = getIntegrationServerProxy()
				.getRecordedTestData(filterProps);

		assertNotNull(dataList);
		assertEquals(1, dataList.size());
		logger.debug(dataList.get(0));
		return dataList;

	}
	
	public List<RecordedTestData> getTestDataList(String nodeName, boolean targetNode)
			throws ConfigManagerProxyPropertyNotInitializedException {
		// get test data for verification
		Properties filterProps = new Properties();

		// Get NodeUUID from nodeName
		String nodeUUID = getNodeUUID(nodeName);
		logger.info("Testing Node={} UUID={} ", nodeName, nodeUUID);
		
		if(targetNode)
			filterProps.put(Checkpoint.PROPERTY_TARGET_NODE_NAME, nodeUUID);
		else
			filterProps.put(Checkpoint.PROPERTY_SOURCE_NODE_NAME, nodeUUID);
		List<RecordedTestData> dataList = getIntegrationServerProxy()
				.getRecordedTestData(filterProps);

		assertNotNull(dataList);
		assertEquals(1, dataList.size());
		logger.debug(dataList.get(0));
		return dataList;

	}

	public String getNodeUUID(String nodeName) throws ConfigManagerProxyPropertyNotInitializedException {
		MessageFlowProxy.Node nodeProxy = flowProxy.getNodeByName(nodeName);
		return nodeProxy.getUUID();
	}

}
