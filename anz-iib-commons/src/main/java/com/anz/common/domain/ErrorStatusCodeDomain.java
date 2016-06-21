/**
 * 
 */
package com.anz.common.domain;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anz.common.cache.AbstractCacheDomain;
import com.anz.common.cache.impl.CacheHandlerFactory;
import com.anz.common.dataaccess.daos.IErrorStatusCodeDao;
import com.anz.common.dataaccess.models.iib.ErrorStatusCode;
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
public class ErrorStatusCodeDomain extends AbstractCacheDomain<ErrorStatusCode> {

	private static ErrorStatusCodeDomain _inst = null;

	private ErrorStatusCodeDomain() {		
	}

	public static ErrorStatusCodeDomain getInstance() throws Exception {
		if (_inst == null) {
			_inst = new ErrorStatusCodeDomain();
		}
		return _inst;
	}

	/**
	 * Get the error code details 	 * 
	 * @param key
	 * @return 100 or 300
	 */
	public ErrorStatusCode getErrorCode(String key) {
		ErrorStatusCode errorCode = new ErrorStatusCode();
		if(ErrorStatusCode.TimeoutException.equalsIgnoreCase(key)) {
			errorCode.setException(ErrorStatusCode.TimeoutException);
			errorCode.setCode("300");
			errorCode.setDescr("Timeout Exception occured while trying to connect");
			errorCode.setSeverity(ErrorStatusCode.SEV_CRITICAL);
		} else {
			errorCode.setException(ErrorStatusCode.InternalException);
			errorCode.setCode("100");
			errorCode.setDescr("Internal Server Error");
			errorCode.setSeverity(ErrorStatusCode.SEV_CRITICAL);
		}
		return errorCode;
	}

	@Override
	public Class<ErrorStatusCode> getEntityClassType() {
		return ErrorStatusCode.class;
	}
	
}
