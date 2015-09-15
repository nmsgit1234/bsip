package com.nms.util.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class ConnectionManager {

	/*
	 * private static String dbURL = "jdbc:derby://localhost:1527/bsidb";
	 */
	private static String dbURL = "jdbc:mysql://localhost:3306/bsidb";
	private static String userName = "nshaikh1";
	private static String passwd = "nshaikh1";
	private static ApplicationContext context = null;

	private static DataSource dataSource = null;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSourceObj) {
		dataSource = dataSourceObj;
	}

	public static Connection getDBConnection() {
		if (context == null)
			initializeContext();
		Connection conn = null;

		try {
			/*
			 * Class.forName("org.apache.derby.jdbc.ClientDriver");
			 */

			// Class.forName("com.mysql.jdbc.Driver");
			// conn = DriverManager.getConnection(dbURL,userName,passwd);
			conn = dataSource.getConnection();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return conn;
	}

	private static void initializeContext() {
		context = new FileSystemXmlApplicationContext(
				new String[] { "src/main/resources/Utilities_Spring_config.xml" });
		//context.getBean("dataSource");

	}

	public static void closeConnection(Connection conn) {
		try {
			if (conn != null)
				conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void rollBack(Connection conn) {
		try {
			if (conn != null)
				conn.rollback();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void closeStatement(Statement stmt) {
		try {
			if (stmt != null)
				stmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static PreparedStatement createPreparedStatment(Connection conn,
			String query) {
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(query);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return pstmt;

	}

}
