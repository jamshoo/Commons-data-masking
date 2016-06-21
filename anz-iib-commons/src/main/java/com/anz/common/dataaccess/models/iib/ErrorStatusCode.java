package com.anz.common.dataaccess.models.iib;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.anz.common.dataaccess.ICommonEntity;

/**
 * @author root
 *
 */
@Entity
public class ErrorStatusCode implements ICommonEntity {
	
	public static final String SEV_CRITICAL = "Critical";
	public static final String SEV_NORMAL = "Normal";
	public static final String SEV_MINOR = "Minor";
	
	public static final String TimeoutException = "TimeoutException";
	public static final String InternalException = "InternalException";
	
	
	private String code;
	
	private String severity;
	
	private String descr;
	
	@Id
	private String exception;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}	

	/**
	 * @return the exception
	 */
	public String getException() {
		return exception;
	}

	/**
	 * @param exception the exception to set
	 */
	public void setException(String exception) {
		this.exception = exception;
	}

	/* (non-Javadoc)
	 * @see com.anz.common.dataaccess.ICommonEntity#getKey()
	 */
	public String getKey() {
		return exception;
	}
}
