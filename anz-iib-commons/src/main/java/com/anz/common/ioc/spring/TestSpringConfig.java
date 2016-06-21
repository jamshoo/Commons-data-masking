package com.anz.common.ioc.spring;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.anz.common.dataaccess.daos.IErrorStatusCodeDao;
import com.anz.common.dataaccess.daos.IIFXCodeDao;
import com.anz.common.dataaccess.daos.IIFXProviderCodeDao;
import com.anz.common.dataaccess.daos.ILookupDao;
import com.anz.common.dataaccess.daos.IOperationDao;
import com.anz.common.dataaccess.daos.IProviderDao;
import com.anz.common.dataaccess.daos.iib.ErrorStatusCodeDao;
import com.anz.common.dataaccess.daos.iib.IFXCodeDao;
import com.anz.common.dataaccess.daos.iib.IFXProviderCodeDao;
import com.anz.common.dataaccess.daos.iib.LookupDao;
import com.anz.common.dataaccess.daos.iib.OperationDao;
import com.anz.common.dataaccess.daos.iib.ProviderDao;
/**
 * Connect to DB2 database or use in memory H2
 * By default all tests are executed in memory HSQL.
 * Reference URL for DB2: http://www.ibm.com/developerworks/data/library/techarticle/dm-0506fechner/
 * @author sanketsw *
 */
@Configuration
@EnableJpaRepositories(basePackages = { "com.anz.common.dataaccess.daos.iib.repos" })
public class TestSpringConfig extends AbstractSpringConfig {

	/**
	 * Set to true if you want to use DB2 database for unit testing.
	 */
	public static boolean DB2_DATASOURCE = false;

	@Bean(name = "dataSource")
	public DataSource dataSource() {

		if (DB2_DATASOURCE) {
			// create data source
	        com.ibm.db2.jcc.DB2SimpleDataSource ds =
	            new com.ibm.db2.jcc.DB2SimpleDataSource();

	        // set connection properties
	        ds.setServerName("localhost");
	        ds.setPortNumber(50000);
	        ds.setDatabaseName("mbrecord");
	        ds.setDriverType(4);
	        ds.setUser("db2inst1");
	        ds.setPassword("password");
	        return ds;
		} else {
			EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
			return builder.setType(EmbeddedDatabaseType.HSQL).build();

		}
	}

	@Bean
	public ILookupDao lookupDao() {
		return new LookupDao();
	}

	@Bean
	public IOperationDao operationDao() {
		return new OperationDao();
	}

	@Bean
	public IIFXCodeDao iFXCodeDao() {
		return new IFXCodeDao();
	}

	@Bean
	public IProviderDao providerDao() {
		return new ProviderDao();
	}

	@Bean
	public IIFXProviderCodeDao iFXProviderCodeDao() {
		return new IFXProviderCodeDao();
	}

	@Bean
	public IErrorStatusCodeDao errorStatusCodeDao() {
		return new ErrorStatusCodeDao();
	}
	

}
