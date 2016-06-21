/**
 * 
 */
package com.anz.common.ioc.spring;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

/**
 * DB2 data source to connect to DB2 database 
 * http://www.ibm.com/developerworks/data/library/techarticle/dm-0506fechner/
 * @author sanketsw
 *
 */
public class DB2DataSource implements DataSource {

	/* (non-Javadoc)
	 * @see javax.sql.CommonDataSource#getLogWriter()
	 */
	public PrintWriter getLogWriter() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.sql.CommonDataSource#getLoginTimeout()
	 */
	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see javax.sql.CommonDataSource#getParentLogger()
	 */
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.sql.CommonDataSource#setLogWriter(java.io.PrintWriter)
	 */
	public void setLogWriter(PrintWriter arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.sql.CommonDataSource#setLoginTimeout(int)
	 */
	public void setLoginTimeout(int arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
	 */
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see java.sql.Wrapper#unwrap(java.lang.Class)
	 */
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.sql.DataSource#getConnection()
	 */
	public Connection getConnection() throws SQLException {
		// create data source
        com.ibm.db2.jcc.DB2SimpleDataSource ds =
            new com.ibm.db2.jcc.DB2SimpleDataSource();

        // set connection properties
        ds.setServerName("localhost");
        ds.setPortNumber(50000);
        ds.setDatabaseName("mbrecord");
        ds.setDriverType(4);

        // set trace properties
        /*ds.setTraceDirectory("c:\\temp");
        ds.setTraceFile("trace");
        ds.setTraceFileAppend(false);
        ds.setTraceLevel(com.ibm.db2.jcc.DB2BaseDataSource.TRACE_ALL);*/

        // get connection
        java.sql.Connection con = ds.getConnection("db2inst1", "password");

	   return con;
	}

	/* (non-Javadoc)
	 * @see javax.sql.DataSource#getConnection(java.lang.String, java.lang.String)
	 */
	public Connection getConnection(String arg0, String arg1)
			throws SQLException {
		return getConnection();
	}

}
