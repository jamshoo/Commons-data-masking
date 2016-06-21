/**
 * 
 */
package com.anz.common.compute;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbMessage;


/**
 * @author sanketsw
 * @param <O>
 *
 */
public abstract class AbstractParser<O> implements IParser<O> {
	
	private Class<O> pojoClass;
	
	public AbstractParser(Class<O> pojoClass) {
		this.pojoClass = pojoClass;
	}

	/**
	 * @return the pojoClass
	 */
	public Class<O> getPojoClass() {
		return pojoClass;
	}

	/**
	 * @param pojoClass the pojoClass to set
	 */
	public void setPojoClass(Class<O> pojoClass) {
		this.pojoClass = pojoClass;
	}
	

	public void removeMessageBody(MbMessage outMessage) throws Exception {
		// Detach Original Message Body Node from the response
		MbElement messageBody = outMessage.getRootElement().getLastChild();
		if(messageBody != null)
			messageBody.detach();
		
	}


	
	
}
