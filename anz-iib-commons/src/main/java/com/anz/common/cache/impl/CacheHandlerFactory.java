/**
 * 
 */
package com.anz.common.cache.impl;

import javax.cache.Cache;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Handles global and local cache Cache Handler Factory -> Cache Handler ->
 * Caching Provider -> Cache Manager -> Cache
 * 
 * @author sanketsw
 * 
 */
public class CacheHandlerFactory {
	
	public static final String MessageHeaderCache = "MessageHeaderCache";
	public static final String TransactionIdCache = "TransactionIdCache";
	public static final String StaticCache = "StaticCache";

	private static final Logger logger = LogManager.getLogger();

	private static CacheHandlerFactory _inst = null;

	/**
	 * private constructor of singleton
	 */
	private CacheHandlerFactory() {
	}

	/**
	 * @return access to singleton instance
	 */
	public static CacheHandlerFactory getInstance() {
		if (_inst == null) {
			_inst = new CacheHandlerFactory();
		}
		return _inst;
	}

	/**
	 * @param cacheName
	 * @param objectKey
	 * @return get the object from local or global cache
	 */
	public String lookupCache(String cacheName, String objectKey) {

		String cachedObject = null;
		Cache<String, String> localCache = null;
		try {

			localCache = LocalCacheHandler.getInstance().getCache(cacheName);
			cachedObject = localCache != null ? localCache.get(objectKey) : null;
		} catch (Exception e) {
			logger.throwing(e);
		}
		if (cachedObject != null) {
			logger.info("found in the local cache: {} objectKey: {}", cacheName, objectKey);
			return cachedObject;
		}
		try {

			Cache<String, String> globalCache = GlobalCacheHandler.getInstance().getCache(cacheName);
			cachedObject = globalCache != null ? globalCache.get(objectKey) : null;
		} catch (Exception e) {
			logger.throwing(e);
		}
		if (cachedObject != null) {
			logger.info("found in the global cache: {} objectKey: {}", cacheName, objectKey);
			if(localCache != null) { 
				localCache.put(objectKey, cachedObject);
			}
			return cachedObject;
		}		
		logger.info("Not found in any cache: {} objectKey: {}", cacheName, objectKey);
		return null;
	}

	/**
	 * @param cacheName
	 * @param objectKey
	 * @param objectValue
	 */
	public void updateCache(String cacheName, String objectKey,
			String objectValue) {

		logger.info("updating local and global cache: {} objectKey: {}", cacheName, objectKey);
		try {
			Cache<String, String> localCache = LocalCacheHandler.getInstance().getCache(cacheName);
			localCache.remove(objectKey);
			localCache.put(objectKey, objectValue);
		} catch (Exception e) {
			logger.throwing(e);
		}
		try {
			Cache<String, String> globalCache = GlobalCacheHandler.getInstance().getCache(cacheName);
			globalCache.remove(objectKey);
			globalCache.put(objectKey, objectValue);
		} catch (Exception e) {
			logger.throwing(e);
		}

	}
	
	/**
	 * Remove an element from local and global cache
	 * @param cacheName
	 * @param objectKey
	 */
	public void removeFromCache(String cacheName, String objectKey) {

		logger.info("removing from local and global cache: {} objectKey: {}", cacheName, objectKey);
		try {
			Cache<String, String> localCache = LocalCacheHandler.getInstance().getCache(cacheName);
			localCache.remove(objectKey);
		} catch (Exception e) {
			logger.throwing(e);
		}
		try {
			Cache<String, String> globalCache = GlobalCacheHandler.getInstance().getCache(cacheName);
			globalCache.remove(objectKey);
		} catch (Exception e) {
			logger.throwing(e);
		}

	}
	

}
