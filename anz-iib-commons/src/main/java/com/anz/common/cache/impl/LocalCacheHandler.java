/**
 * 
 */
package com.anz.common.cache.impl;

import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.cache.Cache;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.Duration;
import javax.cache.expiry.ModifiedExpiryPolicy;
import javax.cache.spi.CachingProvider;
import javax.management.MBeanServer;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;

import net.sf.ehcache.hibernate.management.api.EhcacheStats;
import net.sf.ehcache.management.ManagementService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ehcache.jcache.JCacheManagementMXBean;
import org.ehcache.jcache.JCacheManager;

/**
 * ehCache Cache Handler in JCashe JSR107 standard API
 * Cache Handler Factory -> Cache Handler -> Caching Provider -> Cache Manager -> Cache
 * @author sanketsw
 * 
 */
public class LocalCacheHandler extends AbstractCacheHandler {

	private static final Logger logger = LogManager.getLogger();

	private static LocalCacheHandler _inst = null;

	

	private LocalCacheHandler() throws Exception {
		super();
	}

	public static LocalCacheHandler getInstance() throws Exception {
		if (_inst == null) {
			
				_inst = new LocalCacheHandler();
		}
		return _inst;
	}


	@Override
	public String getDefaultCacheName() {
		return "DefaultMap";
	}

	@Override
	public String getCachingProviderName() {
		return "org.ehcache.jcache.JCacheCachingProvider";
	}

	/* (non-Javadoc)
	 * @see com.anz.common.cache.impl.AbstractCacheHandler#getCache(java.lang.String)
	 */
	@Override
	public Cache<String, String> getCache(String cacheName) throws CacheException, Exception {
		Cache<String, String> cache = null;
		
		try {
			
			logger.debug("Retriving cache {}", cacheName);
			cache = cacheManager.getCache(cacheName);	
			
		} catch(Exception e) {
			logger.debug("Retriving cache using type classes {}", cacheName);
			try {
				cache = cacheManager.getCache(cacheName, String.class, String.class);
			}catch(Exception e2) {
				logger.throwing(e2);
			}
		}
		if (cache == null) {
			logger.debug("Starting cache {}", cacheName);
			MutableConfiguration<String, String> jcacheConfig = new MutableConfiguration<String, String>();
			jcacheConfig.setTypes(String.class, String.class);			
			cache = cacheManager.createCache(cacheName, jcacheConfig);
		}
		return cache;
	}
	
	/* (non-Javadoc)
	 * @see com.anz.common.cache.impl.AbstractCacheHandler#getCacheManager()
	 */
	@Override
	public javax.cache.CacheManager getCacheManager() throws Exception {
		javax.cache.CacheManager ret =  super.getCacheManager();

		try {
			// Register for JMX management
			JCacheManager ehCacheManager = (JCacheManager)ret;
			MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
			ehCacheManager.getEhCacheNativeCacheManager().setName("LocalCacheManager");
			logger.info("printing mBeanServer {}", mBeanServer);
			logger.info("printing EhCacheNativeCacheManager {}", ehCacheManager.getEhCacheNativeCacheManager());
			ManagementService.registerMBeans(ehCacheManager.getEhCacheNativeCacheManager(), mBeanServer, true, true, true, true);
		}catch(Exception e) {
			logger.info("net.sf.ehcache:type=CacheManager,name=LocalCacheManager is already registered for JMX management. Ignoring...");
			logger.info(e.getMessage());
		}
		
		return ret;
	}

	@Override
	public String getCacheManagerURI() {
		URL resource = LocalCacheHandler.class.getResource("ehcache-localcache.xml");
        if(resource != null) {
            return resource.toString();
        } else {
        	logger.warn("Could not load the resource {}", "ehcache-localcache.xml");
        }
        return null;
	}
	
	
	
	
	
	

}
