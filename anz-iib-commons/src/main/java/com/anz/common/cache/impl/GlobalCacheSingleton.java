package com.anz.common.cache.impl;

import java.net.URL;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GlobalCacheSingleton {
	private static final Logger logger = LogManager.getLogger();
	private static GlobalCacheSingleton _inst = null;
	private static GlobalCacheSettings globalcacheSettings;

	private GlobalCacheSingleton() {
		if (globalcacheSettings == null) {
			try {
				JAXBContext jaxbContext = JAXBContext
						.newInstance(GlobalCacheSettings.class);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				URL resource = GlobalCacheSingleton.class
						.getResource("global-cache.xml");
				globalcacheSettings = (GlobalCacheSettings) unmarshaller
						.unmarshal(resource);
			} catch (JAXBException e) {
				logger.throwing(e);
			}
		}
	}

	public static GlobalCacheSingleton getInstance() {
		if (_inst == null) {
			_inst = new GlobalCacheSingleton();
		}

		return _inst;
	}

	public Cache getCacheSetting(String cacheName) {

		if (globalcacheSettings != null) {
			for (Cache cacheSetting : globalcacheSettings.getCache()) {
				if (cacheName.equalsIgnoreCase(cacheSetting.getName())) {
					logger.info("Cache setting found for the cache {}", cacheName);
					return cacheSetting;
				}
			}
		}
		return null;

	}

}
