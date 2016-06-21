/**
 * 
 */
package com.anz.common.cache.impl;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author root
 *
 */
@XmlRootElement(name="cache")
public class Cache {

	private String name;
	private int timeToLiveSeconds;
	/**
	 * @return the name
	 */
	@XmlElement(name="name")
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the timeToLiveSeconds
	 */
	@XmlElement(name="timeToLiveSeconds")
	public int getTimeToLiveSeconds() {
		return timeToLiveSeconds;
	}
	/**
	 * @param timeToLiveSeconds the timeToLiveSeconds to set
	 */
	public void setTimeToLiveSeconds(int timeToLiveSeconds) {
		this.timeToLiveSeconds = timeToLiveSeconds;
	}
	
	
}
