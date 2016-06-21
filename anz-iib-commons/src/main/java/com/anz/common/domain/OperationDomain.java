/**
 * 
 */
package com.anz.common.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anz.common.cache.AbstractCacheDomain;
import com.anz.common.cache.impl.CacheHandlerFactory;
import com.anz.common.dataaccess.daos.IOperationDao;
import com.anz.common.dataaccess.models.iib.Operation;
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
public class OperationDomain extends AbstractCacheDomain<Operation> {

	private static final Logger logger = LogManager.getLogger();

	private static OperationDomain _inst = null;

	private CacheHandlerFactory cacheHandler = CacheHandlerFactory
			.getInstance();

	private OperationDomain() {
	}

	public static OperationDomain getInstance() {
		if (_inst == null) {
			_inst = new OperationDomain();
		}
		return _inst;
	}

	/**
	 * Get the operation details from the cache or static database
	 * 
	 * @param key
	 * @return
	 */
	public Operation getOperation(String key) {

		String json = null;
		Operation operation = null;

		json = cacheHandler.lookupCache(getDefaultCacheName(), key);

		if (json != null) {
			operation = TransformUtils.fromJSON(json, Operation.class);
		} else {	

			// Read from JPA/Database and map to cacheable pojo
			IIoCFactory factory;
			try {
				factory = AnzSpringIoCFactory.getInstance();
				IOperationDao operationDao = factory.getBean(IOperationDao.class);
				logger.info("operationDao: {}", operationDao);
				operation = operationDao.findOne(key);
				if(operation == null) {
					// Create a new one 
					// database is empty
					Operation operation2 = new Operation();
					operation2.setQualifier(key);
					operation2.setOperation(Operation.ADD);
					operation2.setImeplementation("IIB REST API implementation");
					operation = operationDao.saveAndFlush(operation2);
					logger.info("created new operation: {}", operation.getImeplementation());
				}
				logger.info("got value in operationDao from data source: {}", operation.getImeplementation());
				
			} catch (Exception e) {
				logger.warn("Could not read from daat source");
				logger.throwing(e);
			}

			if(operation != null) {
				cacheHandler.updateCache(getDefaultCacheName(), key,
						TransformUtils.toJSON(operation));
			}

		}

		return operation;
	}

	@Override
	public Class<Operation> getEntityClassType() {
		return Operation.class;
	}

	@Override
	public String getDefaultCacheName() {
		return CacheHandlerFactory.StaticCache;
	}
	
}
