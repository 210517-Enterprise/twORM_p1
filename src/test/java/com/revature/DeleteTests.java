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
import com.revature.model.Person_Two;
import com.revature.twORM.TwORM;

public class DeleteTests {

	@BeforeAll
	public static void setup() {
		System.out.println("Beginning tests of Delete Service. Creating table to test against.");
		ConnectionFactory cf = ConnectionFactory.getInstance();
		Connection conn = cf.getConnection();
		String sql = "DROP TABLE IF EXISTS person_two;\r\n" + "\r\n" + "CREATE TABLE person_two (\r\n"
				+ "id SERIAL PRIMARY KEY,\r\n" + "user_name VARCHAR(50),\r\n" + "user_age int\r\n" + ");\r\n" + "\r\n"
				+ "INSERT INTO person_two (user_name, user_age) VALUES \r\n" + "	('Tom', 18),\r\n"
				+ "	('Dick', 18),\r\n" + "	('Harry', 18);";

		try {
			Statement stmt = conn.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@AfterAll
	public static void teardown() {
		System.out.println("Delete service tests complete. Yeeting table!");
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
	public void deleteObject() {
		TwORM t = TwORM.getInstance();
		t.addClass(Person_Two.class);
		Person_Two p = new Person_Two(3, "Harry", 18);
		
		boolean r = false;
		r = t.deleteObjectFromDB(p);
		assertTrue(r);
	}
	
	@Test
	public void deleteObjectNotInDB() {
		TwORM t = TwORM.getInstance();
		t.addClass(Person_Two.class);
		
		Person_Two p = new Person_Two(7, "Pierre", 31);
		boolean r = true;
		r = t.deleteObjectFromDB(p);
		assertFalse(r);
	}
	
	
	
}
