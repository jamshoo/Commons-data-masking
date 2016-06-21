/**
 * 
 */
package com.anz.common.compute.impl;

import javax.xml.bind.JAXBContext;

import com.anz.common.compute.AbstractParser;
import com.anz.common.transform.TransformUtils;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;

/**
 * @author sanketsw
 * @param <O>
 *
 */
public class JsonBlobParser<O> extends AbstractParser<O> {

	public JsonBlobParser(Class<O> pojoClass) {
		super(pojoClass);
	}

	/* (non-Javadoc)
	 * @see com.anz.common.compute.IParser#readInputMessage(javax.xml.bind.JAXBContext, com.ibm.broker.plugin.MbMessage)
	 */
	public O readInputMessage(JAXBContext jaxbContext, MbMessage inMessage) throws Exception {
		
		String input = ComputeUtils.getStringFromBlob(inMessage);
		
		return TransformUtils.fromJSON(input, getPojoClass());
	}

	/* (non-Javadoc)
	 * @see com.anz.common.compute.IParser#setOutputMessage(javax.xml.bind.JAXBContext, com.ibm.broker.plugin.MbMessage, java.lang.Object)
	 */
	public void setOutputMessage(JAXBContext jaxbContext, MbMessage outMessage, O output) throws Exception {
		
		String outputJson = TransformUtils.toJSON(output);
		
		ComputeUtils.replaceStringAsBlob(outMessage, outputJson);
		
		ComputeUtils.setElementInTree("application/json", outMessage, "HTTPReplyHeader", "Content-Type");
	}



}
