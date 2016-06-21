/**
 * 
 */
package com.anz.common.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anz.common.cache.AbstractCacheDomain;
import com.anz.common.cache.impl.CacheHandlerFactory;
import com.anz.common.dataaccess.daos.ILookupDao;
import com.anz.common.dataaccess.models.iib.Lookup;
import com.anz.common.ioc.IIoCFactory;
import com.anz.common.ioc.spring.AnzSpringIoCFactory;
import com.anz.common.transform.TransformUtils;

/**
 * Domain class responsible for reading from cache or database as required.
 * Configure the data source from where the cache objects should be read from
 * 
 * Domain method flow: Lookup Cache get from database if not in cache Store to
 * cache Return
 * 
 * @author sanketsw
 * 
 */
public class LookupDomain extends AbstractCacheDomain<Lookup> {

	private static final Logger logger = LogManager.getLogger();

	private static LookupDomain _inst = null;

	private CacheHandlerFactory cacheHandler = CacheHandlerFactory
			.getInstance();

	private LookupDomain() {
	}

	public static LookupDomain getInstance() {
		if (_inst == null) {
			_inst = new LookupDomain();
		}
		return _inst;
	}

	/**
	 * Get the lookup details from the cache or static database
	 * 
	 * @param key
	 * @return
	 */
	public Lookup getLookup(String key) {

		String json = null;
		Lookup lookup = null;

		json = cacheHandler.lookupCache(getDefaultCacheName(), key);

		if (json != null) {
			lookup = TransformUtils.fromJSON(json, Lookup.class);
		} else {	

			// Read from JPA/Database and map to cacheable pojo
			IIoCFactory factory;
			try {
				factory = AnzSpringIoCFactory.getInstance();
				ILookupDao dao = factory.getBean(ILookupDao.class);
				logger.info("operationDao: {}", dao);
				lookup = dao.findOne(key);
				if(lookup == null) {
					// Create a new one 
					// database is empty
					Lookup operation2 = new Lookup();
					operation2.setQualifier(key);
					operation2.setName(key);
					operation2.setValue("+61");
					lookup = dao.saveAndFlush(operation2);
					logger.info("created new operation: {}", lookup.getValue());
				}
				logger.info("got value in operationDao from data source: {}", lookup.getValue());
				
			} catch (Exception e) {
				logger.warn("Could not read from daat source");
				logger.throwing(e);
			}

			if(lookup != null) {
				cacheHandler.updateCache(getDefaultCacheName(), key,
						TransformUtils.toJSON(lookup));
			}

		}

		return lookup;
	}

	@Override
	public Class<Lookup> getEntityClassType() {
		return Lookup.class;
	}


	@Override
	public String getDefaultCacheName() {
		return CacheHandlerFactory.StaticCache;
	}
	
}
