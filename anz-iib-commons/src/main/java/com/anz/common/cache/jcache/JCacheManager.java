/**
 * 
 */
package com.anz.common.cache.jcache;

import java.net.URI;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;
import javax.cache.spi.CachingProvider;


/**
 * @author sanketsw
 *
 */
public abstract class JCacheManager implements CacheManager {

	private JCacheCachingProvider jCacheProvider;
	private ClassLoader classLoader;
	private URI uri;
	private Properties properties;
	@SuppressWarnings("rawtypes")
	private final ConcurrentHashMap<String, Cache> allCaches = new ConcurrentHashMap<String, Cache>();

	/**
	 * @return the allCaches
	 */
	@SuppressWarnings("rawtypes")
	public ConcurrentHashMap<String, Cache> getAllCaches() {
		return allCaches;
	}


	/* (non-Javadoc)
	 * @see javax.cache.CacheManager#getCachingProvider()
	 */
	public CachingProvider getCachingProvider() {
		return jCacheProvider;
	}

	/* (non-Javadoc)
	 * @see javax.cache.CacheManager#getClassLoader()
	 */
	public ClassLoader getClassLoader() {
		return classLoader;
	}

	/* (non-Javadoc)
	 * @see javax.cache.CacheManager#getProperties()
	 */
	public Properties getProperties() {
		return properties;
	}

	/* (non-Javadoc)
	 * @see javax.cache.CacheManager#getURI()
	 */
	public URI getURI() {
		return uri;
	}


	/* (non-Javadoc)
	 * @see com.anz.common.cache.impl.JCacheManager#initiate(com.anz.common.cache.impl.JCacheProvider, java.lang.ClassLoader, java.net.URI, java.util.Properties)
	 */
	public void initiate(JCacheCachingProvider jCacheProvider, ClassLoader classLoader, URI uri,
			Properties properties) {
		this.jCacheProvider = jCacheProvider;
		this.classLoader = classLoader;
		this.uri = uri;
		this.properties = properties;
		
	}


	/* (non-Javadoc)
	 * @see javax.cache.CacheManager#getCache(java.lang.String, java.lang.Class, java.lang.Class)
	 */
	public <K, V> Cache<K, V> getCache(String cacheName, Class<K> arg1, Class<V> arg2) {
		return getCache(cacheName);
	}

	/* (non-Javadoc)
	 * @see javax.cache.CacheManager#getCacheNames()
	 */
	public Iterable<String> getCacheNames() {
		throw new UnsupportedOperationException("Not applicable");
	}
	

	/* (non-Javadoc)
	 * @see javax.cache.CacheManager#isClosed()
	 */
	public boolean isClosed() {
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.cache.CacheManager#unwrap(java.lang.Class)
	 */
	public <T> T unwrap(Class<T> arg0) {
		throw new UnsupportedOperationException("Not applicable");
	}
	
	/* (non-Javadoc)
	 * @see javax.cache.CacheManager#createCache(java.lang.String, C)
	 */
	public <K, V, C extends Configuration<K, V>> Cache<K, V> createCache(
			String cacheName, C configuration) throws IllegalArgumentException {
		return getCache(cacheName);
	}


	/* (non-Javadoc)
	 * @see javax.cache.CacheManager#destroyCache(java.lang.String)
	 */
	public void destroyCache(String cacheName) {
		throw new UnsupportedOperationException("Not applicable");		
	}


	/* (non-Javadoc)
	 * @see javax.cache.CacheManager#enableManagement(java.lang.String, boolean)
	 */
	public void enableManagement(String cacheName, boolean enabled) {
		throw new UnsupportedOperationException("Not applicable");		
	}


	/* (non-Javadoc)
	 * @see javax.cache.CacheManager#enableStatistics(java.lang.String, boolean)
	 */
	public void enableStatistics(String cacheName, boolean enabled) {
		throw new UnsupportedOperationException("Not applicable");		
	}


	/* (non-Javadoc)
	 * @see javax.cache.CacheManager#close()
	 */
	public void close() {
		throw new UnsupportedOperationException("Not applicable");		
	}

}
