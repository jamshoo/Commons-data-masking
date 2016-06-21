package com.anz.common.cache.jcache;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Properties;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.cache.CacheManager;
import javax.cache.configuration.OptionalFeature;
import javax.cache.spi.CachingProvider;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anz.common.cache.impl.GlobalCacheManager;


/**
 * @author sanketsw
 *
 */
public class JCacheCachingProvider implements CachingProvider  {
	
	private static final Logger logger = LogManager.getLogger();

	private static final String URI_DEFAULT = GlobalCacheManager.URI;
	
	private final Map<ClassLoader, ConcurrentMap<URI, JCacheManager>> cacheManagers = new WeakHashMap<ClassLoader, ConcurrentMap<URI, JCacheManager>>();

	/* (non-Javadoc)
	 * @see javax.cache.spi.CachingProvider#close()
	 */
	public void close() {
		synchronized (cacheManagers) {
            for (Map.Entry<ClassLoader, ConcurrentMap<URI, JCacheManager>> entry : cacheManagers.entrySet()) {
                for (JCacheManager jCacheManager : entry.getValue().values()) {
                    jCacheManager.close();
                }
            }
            cacheManagers.clear();
        }
	}

	/* (non-Javadoc)
	 * @see javax.cache.spi.CachingProvider#close(java.lang.ClassLoader)
	 */
	public void close(ClassLoader classLoader) {
		synchronized (cacheManagers) {
            final ConcurrentMap<URI, JCacheManager> map = cacheManagers.remove(classLoader);
            if(map != null) {
                for (JCacheManager cacheManager : map.values()) {
                    cacheManager.close();
                }
            }
        }
	}

	/* (non-Javadoc)
	 * @see javax.cache.spi.CachingProvider#close(java.net.URI, java.lang.ClassLoader)
	 */
	public void close(URI uri, ClassLoader classLoader) {
        synchronized (cacheManagers) {
            final ConcurrentMap<URI, JCacheManager> map = cacheManagers.get(classLoader);
            if(map != null) {
                final JCacheManager jCacheManager = map.remove(uri);
                if(jCacheManager != null) {
                    jCacheManager.close();
                }
            }
        }
	}

	/* (non-Javadoc)
	 * @see javax.cache.spi.CachingProvider#getCacheManager()
	 */
	public CacheManager getCacheManager() {
		return getCacheManager(getDefaultURI(), getDefaultClassLoader());
	}

	/* (non-Javadoc)
	 * @see javax.cache.spi.CachingProvider#getCacheManager(java.net.URI, java.lang.ClassLoader)
	 */
	public CacheManager getCacheManager(URI uri, ClassLoader classLoader) {
		 return getCacheManager(uri, classLoader, null);
	}

	/* (non-Javadoc)
	 * @see javax.cache.spi.CachingProvider#getCacheManager(java.net.URI, java.lang.ClassLoader, java.util.Properties)
	 */
	public CacheManager getCacheManager(URI uri, ClassLoader classLoader,
			Properties properties) {
		uri = uri == null ? getDefaultURI() : uri;
        classLoader = classLoader == null ? getDefaultClassLoader() : classLoader;
        properties = new Properties(properties);

        JCacheManager cacheManager;
        
        ConcurrentMap<URI, JCacheManager> uriMap;
        synchronized (cacheManagers) {
            uriMap = cacheManagers.get(classLoader);
            if(uriMap == null) {
                uriMap = new ConcurrentHashMap<URI, JCacheManager>();
                cacheManagers.put(classLoader, uriMap);
            }
            cacheManager = uriMap.get(uri);
            if(cacheManager == null) {
                try {
					cacheManager = (JCacheManager)Class.forName(uri.toString()).newInstance();
	                cacheManager.initiate(this, classLoader, uri, properties);
	                uriMap.put(uri, cacheManager);
				} catch (IllegalAccessException e) {
					logger.catching(Level.WARN, e);
				} catch (InstantiationException e) {
					logger.catching(Level.WARN, e);
				} catch (ClassNotFoundException e) {
					logger.catching(Level.WARN, e);
				}
            }
        }
        return cacheManager;
	}

	/* (non-Javadoc)
	 * @see javax.cache.spi.CachingProvider#getDefaultClassLoader()
	 */
	public ClassLoader getDefaultClassLoader() {
		return getClass().getClassLoader();
	}

	/* (non-Javadoc)
	 * @see javax.cache.spi.CachingProvider#getDefaultProperties()
	 */
	public Properties getDefaultProperties() {
		throw new UnsupportedOperationException("Not applicable");
	}

	/* (non-Javadoc)
	 * @see javax.cache.spi.CachingProvider#getDefaultURI()
	 */
	public URI getDefaultURI() {
		try {
			return new URI(URI_DEFAULT);
		} catch (URISyntaxException e) {
			logger.catching(Level.WARN, e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.cache.spi.CachingProvider#isSupported(javax.cache.configuration.OptionalFeature)
	 */
	public boolean isSupported(OptionalFeature arg0) {
		throw new UnsupportedOperationException("Not applicable");
	}
	
	
	
}
