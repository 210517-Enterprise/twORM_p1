package com.revature;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.revature.connection.ConnectionFactory;
import com.revature.model.NoAnnotations;
import com.revature.model.Person_Two;
import com.revature.twORM.TwORM;


public class TwORMTests {

	
	@BeforeAll
	public static void setup() {
		System.out.println("Beginning tests of TwORM services");
	}
	
	@AfterAll
	public static void teardown() {
		System.out.println("Yeet created table when done!");
		ConnectionFactory cf = ConnectionFactory.getInstance();
		Connection conn = cf.getConnection();
		
		String sql = "DROP TABLE IF EXISTS person_two;";
		try {
			Statement stmt = conn.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testAddModel() {
		TwORM t = TwORM.getInstance();
		boolean result = t.addClass(Person_Two.class);
		assertTrue(result);
	}
	
	@Test
	public void addClassNoAnnotations() {
		TwORM t = TwORM.getInstance();
		boolean result = t.addClass(NoAnnotations.class);
		assertFalse(result);
	}
	

	
}
