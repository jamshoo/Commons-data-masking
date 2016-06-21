/**
 * 
 */
package com.anz.common.cache.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.anz.common.dataaccess.models.iib.Operation;
import com.anz.common.domain.OperationDomain;

/**
 * @author root
 * 
 */
public class DataSourceSampleTest {

	OperationDomain ds;

	Operation obj;

	/**
	 * @throws java.lang.Exception
	 */
	//@Before
	public void setUp() throws Exception {
		ds = OperationDomain.getInstance();
		obj = ds.getOperation("SampleKey");
	}

	/**
	 * @throws java.lang.Exception
	 */
	//@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.anz.common.domain.OperationDomain#getObjectFromSource(java.lang.String, java.lang.String, java.lang.Class)}
	 * .
	 */
	//@Test
	public void testGetObjectFromSource() {
		assertNotNull(obj);
		assertEquals("SampleKey", obj.getKey());
	}

}
