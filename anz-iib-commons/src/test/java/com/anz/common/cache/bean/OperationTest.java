/**
 * 
 */
package com.anz.common.cache.bean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.anz.common.dataaccess.models.iib.Operation;



/**
 * @author root
 *
 */
public class OperationTest {
	
	private Operation op;
	private Operation opForSetters;
	private String implementation = "IIB";
	

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		op = new Operation();	
		op.setOperation(Operation.ADD);
		op.setImeplementation(implementation);
		
		opForSetters = new Operation();	
		op.setOperation(Operation.ADD);
		op.setImeplementation(implementation);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.anz.common.cache.pojo.Operation#OperationDetails(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testOperationDetails() {
		assertNotNull(op);
	}

	/**
	 * Test method for {@link com.anz.common.cache.pojo.Operation#getImeplementation()}.
	 */
	@Test
	public void testGetImeplementation() {
		assertEquals(implementation, op.getImeplementation());
	}

	/**
	 * Test method for {@link com.anz.common.cache.pojo.Operation#setImeplementation(java.lang.String)}.
	 */
	@Test
	public void testSetImeplementation() {
		opForSetters.setImeplementation("IIB2");
		assertEquals("IIB2", opForSetters.getImeplementation());
	}

	/**
	 * Test method for {@link com.anz.common.cache.pojo.Operation#getOperation()}.
	 */
	@Test
	public void testGetOperation() {
		assertEquals(Operation.ADD, op.getOperation());
	}

	/**
	 * Test method for {@link com.anz.common.cache.pojo.Operation#setOperation(java.lang.String)}.
	 */
	@Test
	public void testSetOperation() {
		opForSetters.setOperation("Subtract");
		assertEquals("Subtract", opForSetters.getOperation());
	}
}
