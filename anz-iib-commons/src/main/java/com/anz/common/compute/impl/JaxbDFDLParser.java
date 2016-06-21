/**
 * 
 */
package com.anz.common.compute.impl;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.w3c.dom.Document;

import com.anz.common.compute.AbstractParser;
import com.anz.common.compute.IParser;
import com.ibm.broker.plugin.MbDFDL;
import com.ibm.broker.plugin.MbMessage;

/**
 * @author sanketsw
 * @param <O>
 *
 */
public class JaxbDFDLParser<O> extends AbstractParser<O> {

	public JaxbDFDLParser(Class<O> pojoClass) {
		super(pojoClass);
	}

	/* (non-Javadoc)
	 * @see com.anz.common.compute.IParser#readInputMessage(javax.xml.bind.JAXBContext, com.ibm.broker.plugin.MbMessage, java.lang.Class)
	 */
	public O readInputMessage(JAXBContext jaxbContext, MbMessage inMessage) throws Exception {
		
		// unmarshal the input message data from the Broker tree into your Java object classes   
		Object inMsgJavaObj = jaxbContext.createUnmarshaller().unmarshal(inMessage.getDOMDocument());		
		
		@SuppressWarnings("unchecked")
		JAXBElement<O> elem = (JAXBElement<O>)inMsgJavaObj;
		
		O inputObject = elem.getValue();
		return inputObject;
	}

	/* (non-Javadoc)
	 * @see com.anz.common.compute.IParser#setOutputMessage(javax.xml.bind.JAXBContext, com.ibm.broker.plugin.MbMessage, java.lang.Class, java.lang.Object)
	 */
	public void setOutputMessage(JAXBContext jaxbContext, MbMessage outMessage, O output) throws Exception {
		
		QName nameSpace = new QName(output.getClass().getSimpleName());
		
		JAXBElement<O> outMsgJavaObj = new JAXBElement<O>(nameSpace, getPojoClass(), output);
		
		// set the required Broker domain to for the output message, eg XMLNSC
		Document outDocument = outMessage.createDOMDocument(MbDFDL.PARSER_NAME);
		
		// marshal the new or updated output Java object class into the Broker tree
		jaxbContext.createMarshaller().marshal(outMsgJavaObj, outDocument);
		
	}

}
