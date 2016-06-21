/**
 * 
 */
package com.anz.common.cache.impl;

import java.util.Iterator;

import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;
import javax.xml.bind.JAXBException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anz.common.cache.jcache.JCache;
import com.anz.common.cache.jcache.JConfiguration;
import com.anz.common.compute.impl.ComputeUtils;
import com.anz.common.transform.TransformUtils;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbGlobalMap;
import com.ibm.broker.plugin.MbGlobalMapSessionPolicy;

/**
 * eXtreme Scale MbGlobalMap in JCashe JSR107 standard API
 * Cache Handler Factory -> Cache Handler -> Caching Provider -> Cache Manager -> Cache
 * @author sanketsw
 * @param <V> String
 * @param <K> String
 *
 */
public class GlobalCache<K, V> extends JCache<K, V> {
	
	private static final Logger logger = LogManager.getLogger();
		
	private MbGlobalMap map;
	private JConfiguration<K, V> configuration;


	public GlobalCache(String cacheName, CacheManager cacheManager) throws MbException {
		super(cacheName, cacheManager);
		//setMap(MbGlobalMap.getGlobalMap(cacheName));
		logger.info("cacheName {}", cacheName);
		
		 GlobalCacheSingleton cacheInstance = GlobalCacheSingleton.getInstance();
		 Cache cacheSettings = cacheInstance.getCacheSetting(cacheName);
		 int timeToLive = 100;
		 if(cacheSettings != null ) {
			 timeToLive = cacheSettings.getTimeToLiveSeconds();
			 logger.debug(" time to live for cache {} is {}", cacheName, timeToLive);
		 } else {
			 logger.debug(" using default time to live for cache {} : {}", cacheName, timeToLive);
		 }
		 JConfiguration<K, V> config = new JConfiguration<K, V>(timeToLive);
		//JConfiguration<K, V> config = new JConfiguration<K, V>(10);
		 setConfiguration(config);
	}

	@SuppressWarnings("unchecked")
	public V get(K key) {
		try {
			return (V) map.get(key);
		} catch (MbException e) {
			logger.catching(Level.WARN, e);
		}
		return null;
	}

	public boolean containsKey(K key) {
		try {
			return map.containsKey(key);
		} catch (MbException e) {
			logger.catching(Level.WARN, e);
		}
		return false;
	}


	public void put(K key, V value) {
		try {
			if(! map.containsKey(key)) {
				map.put(key, value);
			}
		} catch (MbException e) {
			logger.catching(Level.WARN, e);
		}
		
	}


	public boolean remove(K key) {
		Object obj = null;
		try {
			obj = map.remove(key);
		} catch (MbException e) {
			logger.catching(Level.WARN, e);
		}
		return obj==null? false: true;
	}


	public boolean replace(K key, V oldValue, V newValue) {
		return replace(key, newValue);
	}

	public boolean replace(K key, V value) {
		try {
			map.update(key, value);
		} catch (MbException e) {
			logger.catching(Level.WARN, e);
			return false;
		}
		return true;
	}


	
	public void setConfiguration(JConfiguration<K, V> configuration) throws MbException {		
		this.configuration = configuration;
		if(configuration.getTimeout() > 0) {
			setMap(MbGlobalMap.getGlobalMap(cacheName, new MbGlobalMapSessionPolicy(configuration.getTimeout())));
		}
	}


	@SuppressWarnings("unchecked")
	public <C extends Configuration<K, V>> C getConfiguration(Class<C> clazz) {
		return (C) configuration;
	}

	public MbGlobalMap getMap() {
		return map;
	}

	public void setMap(MbGlobalMap map) {
		this.map = map;
	}


	
}