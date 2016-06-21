/**
 * 
 */
package com.anz.common.compute;

import javax.xml.bind.JAXBContext;

import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;

/**
 * @author sankestw
 * @param <O>
 *
 */
public interface IParser<O> {
	
	public O readInputMessage(JAXBContext jaxbContext, MbMessage inMessage) throws Exception ;
	
	public void setOutputMessage(JAXBContext jaxbContext, MbMessage outMessage, O output) throws Exception ;

	public void removeMessageBody(MbMessage outMessage) throws Exception;

}
