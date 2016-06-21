package com.anz.common.cache.impl;

import java.net.URI;
import java.net.URISyntaxException;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;

import junit.framework.TestCase;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.anz.common.cache.jcache.JCacheCachingProvider;

/**
 * @author sanketsw
 * 
 */
public class InMemoryCachingProviderTest extends TestCase {

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
			/*logger.warn("System property javax.cache.spi.CachingProvider is not set. Setting it to com.anz.common.cache.jcache.JCacheCachingProvider...");
			System.setProperty("javax.cache.spi.CachingProvider",
					"com.anz.common.cache.jcache.JCacheCachingProvider");*/

			provider = Caching.getCachingProvider(JCacheCachingProvider.class.getName());		
			cacheManager = provider.getCacheManager(new URI(
					InMemoryCacheManager.URI), null);
		
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
