/**
 * 
 */
package com.anz.common.cache;




/**
 * Domain class responsible for reading from cache or database as required.
 * @author sanketsw
 *
 */
public abstract class AbstractCacheDomain<K> {	
		
	/**
	 * Define the linked cache name e.g. IFXCodeCache or ISOCodeCache
	 * @return cache manager URI
	 */
	public String getDefaultCacheName() {
		return  getEntityClassType().getSimpleName() + "Cache";
	}
	
	public abstract Class<K> getEntityClassType();


}
