/**
 * 
 */
package com.anz.common.compute;

/**
 * @author sanketsw
 *
 */
public enum LoggingTerminal {
	INPUT("Input"),
	OUTPUT("Output"),
	INTERMEDIATE("Intermediate");
	
	String value;
	
	/**
	 * @param value
	 */
	LoggingTerminal(String value) {
		this.value = value;
	}
	
	/**
	 * Get the string value of LoggingTerminal Enum
	 * @return string value
	 */
	public String getValue() {
		return this.value;
	}

}
