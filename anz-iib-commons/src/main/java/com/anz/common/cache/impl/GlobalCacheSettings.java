/**
 * 
 */
package com.anz.common.cache.impl;


import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;





/**
 * @author root
 *
 */
@XmlRootElement(name = "globalcachesettings")
public class GlobalCacheSettings {
	
	
	private List<Cache> cache;

	/**
	 * @return the cache
	 */
	@XmlElementWrapper(name = "caches")
	@XmlElement(name = "cache")
	public List<Cache> getCache() {
		return cache;
	}

	/**
	 * @param cache the cache to set
	 */
	public void setCache(List<Cache> cache) {
		this.cache = cache;
	}

	
	

}
