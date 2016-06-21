/**
 * 
 */
package com.anz.common.error;

import com.anz.common.compute.LogMessage;
import com.anz.common.dataaccess.models.iib.ErrorStatusCode;


/**
 * @author sanketsw
 *
 */
public class ExceptionMessage extends LogMessage {
	
	ErrorStatusCode status;
	
	String shortException;

	public ErrorStatusCode getStatus() {
		return status;
	}

	public void setStatus(ErrorStatusCode status) {
		this.status = status;
	}

	public String getShortException() {
		return shortException;
	}

	public void setShortException(String shortException) {
		this.shortException = shortException;
	}
	
	

}
