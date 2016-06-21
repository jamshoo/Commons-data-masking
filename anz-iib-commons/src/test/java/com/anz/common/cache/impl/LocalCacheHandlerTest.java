/**
 * 
 */
package com.anz.common.cache.impl;

import static org.junit.Assert.*;

import javax.cache.Cache;

import org.junit.Before;
import org.junit.Test;

/**
 * @author root
 * 
 */
public class LocalCacheHandlerTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.anz.common.cache.impl.AbstractCacheHandler#getCache(java.lang.String)}
	 * .
	 * @throws Exception 
	 */
	@Test
	public void testGetCache() throws Exception {
		Cache<String, String> localCache = LocalCacheHandler.getInstance()
				.getCache("SampleKey");
		localCache.clear();
		String cachedObject = localCache != null ? localCache.get("test")
				: null;
		assertNull(cachedObject);
	}

	@Test
	public void testPutCache() throws Exception {
		Cache<String, String> localCache = LocalCacheHandler.getInstance()
				.getCache("SampleKey");
		String cachedObject = localCache != null ? localCache.get("test")
				: null;

		assertNull(cachedObject);

		localCache.put("test", "Sample Test in Cache");

		assertEquals("Sample Test in Cache", LocalCacheHandler.getInstance()
				.getCache("SampleKey").get("test"));
		Thread.sleep(10000);
		assertNull(LocalCacheHandler.getInstance()
				.getCache("SampleKey").get("test"));
	}

	@Test
	public void testGetCacheManagerURI() throws Exception {
		assertNotNull(LocalCacheHandler.getInstance().getCacheManagerURI());
	}

}
