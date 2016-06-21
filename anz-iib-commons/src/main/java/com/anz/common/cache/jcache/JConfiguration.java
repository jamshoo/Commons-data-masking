/**
 * 
 */
package com.anz.common.cache.jcache;

import javax.cache.configuration.Configuration;

/**
 * @author sanketsw
 * @param <K, V>
 *
 */
public class JConfiguration<K, V> implements Configuration<K, V> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3447566654751361959L;
	
	int timeout;

	public JConfiguration() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JConfiguration(int timeout) {
		super();
		this.timeout = timeout;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public Class<K> getKeyType() {
		throw new UnsupportedOperationException("Not applicable");
	}

	public Class<V> getValueType() {
		throw new UnsupportedOperationException("Not applicable");
	}

	public boolean isStoreByValue() {
		throw new UnsupportedOperationException("Not applicable");
	}

}
