package com.bunge.icc.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

	public static Connection getConnection() throws ClassNotFoundException, SQLException{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		Properties info = new Properties( );
		info.put( "user", "camanga" );
		info.put( "password", "Bunge999" );
//		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@lil-ux-dbdbga1:1536:BGMUXD",info);
		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@bgaprod-scan:1531:BGM",info);
		
		return connection;
	}
	
}
