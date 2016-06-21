/**
 * 
 */
package com.anz.common.cache.jcache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.configuration.CacheEntryListenerConfiguration;
import javax.cache.configuration.Configuration;
import javax.cache.integration.CompletionListener;
import javax.cache.processor.EntryProcessor;
import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.EntryProcessorResult;

import com.ibm.broker.plugin.MbException;

/**
 * @author sanketsw
 * @param <K, V>
 *
 */
public class JCache<K, V> implements Cache<K, V> {
	
	protected String cacheName;
	protected CacheManager cacheManager;
	
	private Map<K, V> inMemoryCache = new HashMap<K, V>(); 
	
	public String getName() {
		return cacheName;
	}

	public CacheManager getCacheManager() {
		return cacheManager;
	}
	
	
	public JCache(String cacheName, CacheManager cacheManager) throws MbException {
		this.cacheName = cacheName;
		this.cacheManager = cacheManager;		
	}

	public V get(K key) {
		return inMemoryCache.get(key);
	}

	public boolean containsKey(K key) {
		return inMemoryCache.containsKey(key);
	}


	public void put(K key, V value) {
		if(! containsKey(key)) {
			inMemoryCache.put(key, value);
		}
		
	}


	public boolean remove(K key) {
		return inMemoryCache.remove(key)==null? false: true;
	}


	public boolean replace(K key, V oldValue, V newValue) {
		throw new UnsupportedOperationException("Not applicable");
	}

	public boolean replace(K key, V value) {
		throw new UnsupportedOperationException("Not applicable");
	}


	public <C extends Configuration<K, V>> C getConfiguration(Class<C> clazz) {
		throw new UnsupportedOperationException("Not applicable");
	}
	

	public void close() {
		throw new UnsupportedOperationException("Not applicable");
	}

	public boolean isClosed() {
		return false;
	}
	
	public Map<K, V> getAll(Set<? extends K> keys) {
		throw new UnsupportedOperationException("Not applicable");
	}
	

	public void loadAll(Set<? extends K> keys, boolean replaceExistingValues,
			CompletionListener completionListener) {
		throw new UnsupportedOperationException("Not applicable");
		
	}
	
	public boolean remove(K key, V oldValue) {
		throw new UnsupportedOperationException("Not applicable");
	}

	public V getAndRemove(K key) {
		throw new UnsupportedOperationException("Not applicable");
	}

	
	public V getAndPut(K key, V value) {
		throw new UnsupportedOperationException("Not applicable");
	}

	public void putAll(Map<? extends K, ? extends V> map) {
		throw new UnsupportedOperationException("Not applicable");
	}

	public boolean putIfAbsent(K key, V value) {
		throw new UnsupportedOperationException("Not applicable");
	}


	public V getAndReplace(K key, V value) {
		throw new UnsupportedOperationException("Not applicable");
	}

	public void removeAll(Set<? extends K> keys) {
		throw new UnsupportedOperationException("Not applicable");
	}

	public void removeAll() {
		throw new UnsupportedOperationException("Not applicable");
	}

	public void clear() {
		throw new UnsupportedOperationException("Not applicable");
	}

	public <T> T invoke(K key, EntryProcessor<K, V, T> entryProcessor,
			Object... arguments) throws EntryProcessorException {
		throw new UnsupportedOperationException("Not applicable");
	}

	public <T> Map<K, EntryProcessorResult<T>> invokeAll(Set<? extends K> keys,
			EntryProcessor<K, V, T> entryProcessor, Object... arguments) {
		throw new UnsupportedOperationException("Not applicable");
	}


	public <T> T unwrap(Class<T> clazz) {
		throw new UnsupportedOperationException("Not applicable");
	}

	public void registerCacheEntryListener(
			CacheEntryListenerConfiguration<K, V> cacheEntryListenerConfiguration) {
		throw new UnsupportedOperationException("Not applicable");
	}

	public void deregisterCacheEntryListener(
			CacheEntryListenerConfiguration<K, V> cacheEntryListenerConfiguration) {
		throw new UnsupportedOperationException("Not applicable");
	}

	public Iterator<javax.cache.Cache.Entry<K, V>> iterator() {
		throw new UnsupportedOperationException("Not applicable");
	}

}
