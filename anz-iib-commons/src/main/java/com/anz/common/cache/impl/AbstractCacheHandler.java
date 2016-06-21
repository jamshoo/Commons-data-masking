package com.anz.common.cache.impl;

import java.net.URI;
import java.net.URISyntaxException;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author sanketsw
 * 
 */
public abstract class AbstractCacheHandler {

	CachingProvider provider;
	CacheManager cacheManager;

	private static final Logger logger = LogManager.getLogger();

	/**
	 * @throws URISyntaxException
	 */
	public AbstractCacheHandler() throws Exception {

		getCachingProvider();
		getCacheManager();

	}

	/**
	 * @return JCache cachingProvider instance
	 */
	public CachingProvider getCachingProvider() throws Exception {
		/*
		 * if (System.getProperty("javax.cache.spi.CachingProvider") == null) {
		 * logger.warn(
		 * "System property javax.cache.spi.CachingProvider is not set. Setting it to com.anz.common.cache.jcache.JCacheCachingProvider..."
		 * ); System.setProperty("javax.cache.spi.CachingProvider",
		 * "com.anz.common.cache.jcache.JCacheCachingProvider"); }
		 */
		if (provider == null) {
			// If the default caching provider is not set in a system property
			// then use the default one
			// To change default caching provider, set System property
			// javax.cache.spi.CachingProvider
			provider = Caching.getCachingProvider(getCachingProviderName());
			logger.info("Caching Provider={}", provider);
		}
		if (provider == null) {
			logger.warn("No CachingProvider has been configured");
		}
		return provider;
	}

	/**
	 * Returns the default cache manager If you need any other, write your own
	 * method in the child class.
	 * 
	 * @return JCache CacheManager instance
	 * @throws URISyntaxException
	 */
	public CacheManager getCacheManager() throws Exception {
		if (provider != null) {
			cacheManager = getCacheManagerURI() != null ? provider
					.getCacheManager(new URI(getCacheManagerURI()), null)
					: provider.getCacheManager();
			logger.info("Cache Manager={} loaded from {}", cacheManager,
					getCacheManagerURI());
			if (cacheManager == null) {
				logger.warn("CacheManagr has not been configured for {}",
						getCachingProviderName());
			}
		}
		return cacheManager;
	}

	/**
	 * @param cacheName
	 * @return JCache Cache object to look for keys or put new values
	 */
	public Cache<String, String> getCache(String cacheName) throws Exception {
		Cache<String, String> cache = null;
		if (cacheManager != null) {
			try {
				cache = cacheManager.getCache(cacheName);
				// This exception handling for unit test purpose
				// @see DataSourceSampleTest			
			} catch(NoSuchMethodError e) {
				logger.error("Could not get the global cache");
				logger.throwing(e);
				throw new Exception(e);
			} catch(NoClassDefFoundError e) {
				logger.error("Could not get the global cache");
				logger.throwing(e);
				throw new Exception(e);
			} catch(Exception e) {
				logger.error("Could not get the global cache");
				logger.throwing(e);
				throw e;
			}
			if (cache == null) {
				logger.warn("Cache {} has not been created",
						cacheName);
			}

		}
		return cache;
	}

	/**
	 * @return default cache name
	 */
	public abstract String getDefaultCacheName();

	/**
	 * @return get the caching provider name such as cache provider of ehcache
	 *         or xtremescale cache etc.
	 */
	public abstract String getCachingProviderName();

	/**
	 * @return get cache manager URI
	 */
	public abstract String getCacheManagerURI();

}
