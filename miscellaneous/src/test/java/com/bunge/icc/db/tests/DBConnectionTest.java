package com.bunge.icc.db.tests;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bunge.icc.db.DatabaseConnection;

public class DBConnectionTest {

	DatabaseConnection dbConnection = null;
	
	@Before
	public void before(){
		System.out.println("init method");
		dbConnection = new DatabaseConnection();
	}
	
	@Test
	public void testConnection(){
		System.out.println("test connection");
		try {
			Connection conn = dbConnection.getConnection();
			System.out.println("isClosed "+conn.isClosed());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@After
	public void after(){
		System.out.println("destroy method");
	}
}
