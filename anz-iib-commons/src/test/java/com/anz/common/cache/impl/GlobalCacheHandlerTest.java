/**
 * 
 */
package com.anz.common.cache.impl;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

/**
 * @author root
 *
 */
public class GlobalCacheHandlerTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link com.anz.common.cache.impl.AbstractCacheHandler#getCacheManager()}.
	 * @throws Exception 
	 */
	@Test
	public void testGetCacheManager() throws Exception {
		assertNotNull(GlobalCacheHandler.getInstance().getCachingProvider());
		assertNotNull(GlobalCacheHandler.getInstance().getCacheManager());
	}
	
	

}
