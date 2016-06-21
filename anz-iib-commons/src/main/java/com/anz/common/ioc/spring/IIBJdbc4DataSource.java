/**
 * 
 */
package com.anz.common.ioc.spring;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;

import com.ibm.broker.classloading.BrokerClassLoader;
import com.ibm.broker.plugin.MbNode.JDBC_TransactionType;

/**
 * @author root
 * 
 */
public class IIBJdbc4DataSource implements DataSource {
	
	private static final String JDBC_CONFIG_SERVICE = "serv2";
	private static IIBJdbc4DataSource _inst = null;
	
	private IIBJdbc4DataSource() {}
	
	public static IIBJdbc4DataSource getDataSource() {
		if(_inst == null) {
			_inst = new IIBJdbc4DataSource();
		}
		return _inst;
	}

	private static org.apache.logging.log4j.Logger logger = LogManager
			.getLogger();


	public PrintWriter getLogWriter() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setLogWriter(PrintWriter out) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setLoginTimeout(int seconds) throws SQLException {
		// TODO Auto-generated method stub

	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		Connection conn = getJDBCType4Connection(JDBC_CONFIG_SERVICE,
				JDBC_TransactionType.MB_TRANSACTION_AUTO);
		return conn;
	}

	public Connection getConnection(String username, String password)
			throws SQLException {
		// TODO Auto-generated method stub
		Connection conn;
		conn = getJDBCType4Connection(JDBC_CONFIG_SERVICE,
				JDBC_TransactionType.MB_TRANSACTION_AUTO);
		return conn;
	}

	public Connection getJDBCType4Connection(String paramString,
			JDBC_TransactionType paramJDBC_TransactionType) throws SQLException {
		Connection localConnection = null;
		Method localObject1 = null;
		Method localObject2 = null;
		Object localObject3 = null;

		logger.debug("transactionType=" + paramJDBC_TransactionType);

		try {
			@SuppressWarnings("rawtypes")
			Class localClass = BrokerClassLoader
					.getInstance()
					.loadClass(
							"com.ibm.broker.jdbctype4.connfact.JDBCType4ConnectionFactory");
			if (localClass != null) {
				Method[] localObject4 = localClass.getMethods();
				for (int i = 0; i < localObject4.length; i++) {
					if (localObject4[i].getName().equals("getInstance")) {
						localObject1 = localObject4[i];
					}
					if (localObject4[i].getName().equals("getConnection")) {
						localObject2 = localObject4[i];
					}
				}
				if (localObject1 != null) {
					localObject3 = ((Method) localObject1).invoke(
							(Object) null, (Object[]) null);
				}
				if ((localObject3 != null) && (null != localObject2)) {
					
					localConnection = (Connection) ((Method) localObject2)
							.invoke(localObject3, new Object[] { MbNodefactory.getInstance().getMbNode(),
									paramString, paramJDBC_TransactionType });
				}
			}
		} catch (InvocationTargetException e) {
			throw throwableException(e, paramString);
		} catch (ClassNotFoundException e) {
			throw throwableException(e, paramString);
		} catch (IllegalAccessException e) {
			throw throwableException(e, paramString);
		} catch (Exception e) {
			throw throwableException(e, paramString);
		}
		if (null == localConnection) {
			throw throwableException(new Exception("Conenction is null"),
					paramString);
		}

		logger.info("Jdbc type4 Connection to database {}: {} ", paramString, localConnection.toString());

		return localConnection;
	}

	private SQLException throwableException(Throwable e, String paramString) {

		SQLException sqlException = new SQLException(
				"Failed to obtain JDBC Connection", e);
		logger.error("Failed to obtain JDBC Connection: {}", paramString);
		logger.throwing(e);
		return sqlException;

	}

}
