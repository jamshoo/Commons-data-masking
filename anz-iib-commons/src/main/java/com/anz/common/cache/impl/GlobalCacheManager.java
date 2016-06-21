package com.anz.common.cache.impl;

import javax.cache.Cache;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anz.common.cache.jcache.JCacheManager;
import com.ibm.broker.plugin.MbException;

/**
 * eXtreme Scale Cache Manager in JCashe JSR107 standard API
 * Used by Caching Provider
 * 
 * Cache Handler Factory -> Cache Handler -> Caching Provider -> Cache Manager -> Cache
 * 
 * @author sanketsw
 *
 */
public class GlobalCacheManager extends JCacheManager {
	
	private static final Logger logger = LogManager.getLogger();

	public static final String URI = GlobalCacheManager.class.getName();



	/* (non-Javadoc)
	 * @see com.anz.common.cache.jcache.JCacheManager#getCache(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Cache<String, String> getCache(String cacheName) {
		Cache<String, String> cache = getAllCaches().get(cacheName);
        if(cache == null) {
            try {
				cache = new GlobalCache<String, String>(cacheName, this);
				getAllCaches().put(cacheName, cache);
			} catch (MbException e) {
				logger.catching(Level.WARN, e);
			}
        }
        return cache;
	}



}
