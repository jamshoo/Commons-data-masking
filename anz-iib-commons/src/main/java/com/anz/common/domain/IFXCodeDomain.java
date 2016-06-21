/**
 * 
 */
package com.anz.common.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anz.common.cache.AbstractCacheDomain;
import com.anz.common.cache.impl.CacheHandlerFactory;
import com.anz.common.dataaccess.daos.IIFXCodeDao;
import com.anz.common.dataaccess.daos.IIFXProviderCodeDao;
import com.anz.common.dataaccess.daos.IProviderDao;
import com.anz.common.dataaccess.models.iib.IFXCode;
import com.anz.common.dataaccess.models.iib.IFXProviderCode;
import com.anz.common.dataaccess.models.iib.IFXProviderCodePk;
import com.anz.common.dataaccess.models.iib.Provider;
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
public class IFXCodeDomain extends AbstractCacheDomain<IFXCode> {

	private static final Logger logger = LogManager.getLogger();

	private static IFXCodeDomain _inst = null;

	private CacheHandlerFactory cacheHandler = CacheHandlerFactory
			.getInstance();

	private IFXCodeDomain() {
	}

	public static IFXCodeDomain getInstance() {
		if (_inst == null) {
			_inst = new IFXCodeDomain();
		}
		return _inst;
	}
	
	/**
	 * Just for the convenience 
	 * @throws Exception 
	 */
	public void populateIFXCodeDatabase() throws Exception {

		IIoCFactory factory = AnzSpringIoCFactory.getInstance();
		IIFXCodeDao ifxCodeDao = factory.getBean(IIFXCodeDao.class);
		IIFXProviderCodeDao ifxProviderCodeDao = factory.getBean(IIFXProviderCodeDao.class);
		IProviderDao providerDao = factory.getBean(IProviderDao.class);
		
		IFXCode o = new IFXCode();
		o.setCode("178");
		o.setDescr("Error conencting to the system. Please try again later.");
		o.setSeverity(IFXCode.SEV_CRITICAL);
		o.setStatus(IFXCode.STATUS_FAILURE);
		o = ifxCodeDao.saveAndFlush(o);		
		
		Provider p = new Provider();
		p.setId("CICS");
		p.setDescr("CICS provider");
		p = providerDao.saveAndFlush(p);
		
		IFXProviderCode i = new IFXProviderCode();
		i.setProvider(p);
		i.setIfxCode(o);
		i.setCode("15");
		i = ifxProviderCodeDao.saveAndFlush(i);
		
	}


	/**
	 * Get the IFX code details from the cache or static database
	 * @param providerErrorCode
	 * @param providerId
	 * @return IFX code from cache or database
	 */
	public IFXCode getErrorCode(String providerErrorCode, String providerId) {

		String json = null;
		IFXCode errorCode = null;

		json = cacheHandler.lookupCache(getDefaultCacheName(), getCacheKey(providerErrorCode, providerId));

		if (json != null) {
			errorCode = TransformUtils.fromJSON(json, IFXCode.class);
		} else {	

			// Read from JPA/Database and map to cacheable pojo
			IIoCFactory factory;
			try {
				factory = AnzSpringIoCFactory.getInstance();
				IIFXProviderCodeDao dao = factory.getBean(IIFXProviderCodeDao.class);
				IFXProviderCodePk ifxProviderCodeKey = new IFXProviderCodePk();
				ifxProviderCodeKey.setCode(providerErrorCode);
				ifxProviderCodeKey.setProvider(providerId);
				IFXProviderCode code = dao.findOne(ifxProviderCodeKey);
				if(code == null) {
					throw new Exception("No object found macthing: " + providerErrorCode + ":" + providerId);
				}
				errorCode = code.getIfxCode();
				logger.info("got value in Dao from data source: {}", errorCode);
				
			} catch (Exception e) {
				logger.warn("Could not read from data source");
				logger.throwing(e);
			}

			if(errorCode != null) {
				cacheHandler.updateCache(getDefaultCacheName(), getCacheKey(providerErrorCode, providerId),
						TransformUtils.toJSON(errorCode));
			}

		}

		return errorCode;
	}

	private String getCacheKey(String providerErrorCode, String providerId) {
		return providerErrorCode + ":" + providerId;
	}

	@Override
	public Class<IFXCode> getEntityClassType() {
		return IFXCode.class;
	}

	@Override
	public String getDefaultCacheName() {
		return CacheHandlerFactory.StaticCache;
	}


	
}
