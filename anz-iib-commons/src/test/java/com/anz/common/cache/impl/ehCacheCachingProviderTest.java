package com.anz.common.cache.impl;

import java.net.URISyntaxException;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;

import junit.framework.TestCase;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

/**
 * @author sanketsw
 * 
 */
public class ehCacheCachingProviderTest extends TestCase {

	private static final Logger logger = LogManager.getLogger();

	private CachingProvider provider;
	private CacheManager cacheManager;
	@SuppressWarnings("rawtypes")
	private Cache cache;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		provider = Caching.getCachingProvider("org.ehcache.jcache.JCacheCachingProvider");
		cacheManager = provider.getCacheManager();		
	}

	@Test
	public void testGetCacheManager() throws URISyntaxException {
		logger.info(cacheManager);
		assertNotNull(cacheManager);
	}

	@Test
	public void testGetCachingProvider() {
		logger.info(provider);
		assertNotNull(provider);
	}

	@Test
	public void testCache() throws URISyntaxException {
		getCache();
		logger.info(cache);
		assertNotNull(cache);
	}


	private void getCache() {

		cache = cacheManager.getCache("testCache", String.class, String.class);
		if (cache == null) {
			MutableConfiguration<String, String> jcacheConfig = new MutableConfiguration<String, String>();
			jcacheConfig.setTypes(String.class, String.class);
			// create cache
			cache = cacheManager.createCache("testCache", jcacheConfig);
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testElementsInCache() throws URISyntaxException {
		getCache();
		assertNull(cache.get("testElem"));

		cache.put("testElem", "Testing elements in cache");
		assertEquals("Testing elements in cache", cache.get("testElem"));

		cache.remove("testElem");
		assertNull(cache.get("testElem"));

	}

}
