package com.anz.common.dataaccess.daos.iib;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.anz.common.dataaccess.daos.AbstractDaoTest;
import com.anz.common.dataaccess.daos.ILookupDao;


/**
 * Test Cases for ILookupDao bean definition creation
 * @author sanketsw
 *
 */
public class LookupDaoTest extends AbstractDaoTest {

	@Autowired
	private ILookupDao lookupDao;
	
	@Test
	public void testBeanDefinition() {
		assertNotNull(getLookupDao());
	}
	
	public ILookupDao getLookupDao() {
		return lookupDao;
	}
	
	public void setLookupDao(ILookupDao lookupDao) {
		this.lookupDao = lookupDao;
	}
}
