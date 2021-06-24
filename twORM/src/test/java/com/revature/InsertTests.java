package com.revature;

import static org.junit.jupiter.api.Assertions.assertThrows;
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

public class InsertTests {
	@BeforeAll
	public static void setup() {
		System.out.println("Beginning tests of Insertion Methods");
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

	@AfterAll
	public static void teardown() {
		System.out.println("Completed Insertion Tests. Yeeting table!");
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
	public void addObject () {
		TwORM t = TwORM.getInstance();
		t.addClass(Person_Two.class);
		Person_Two p1 = new Person_Two("Tom", 18);
		Person_Two p2 = new Person_Two("Dick", 18);
		Person_Two p3 = new Person_Two("Harry", 18);
		Person_Two p4 = new Person_Two("Guillame", 40);
		boolean r1 = false;
		boolean r2 = false;
		boolean r3 = false;
		boolean r4 = false;
		r1 = t.addObjectToDb(p1);
		r2 = t.addObjectToDb(p2);
		r3 = t.addObjectToDb(p3);
		r4 = t.addObjectToDb(p4);
		assertTrue(r1 && r2 && r3 && r4);
	}
	
	@Test
	public void addInvalidObject() {
		TwORM t = TwORM.getInstance();
		t.addClass(NoAnnotations.class);
		NoAnnotations badObj = new NoAnnotations();
		assertThrows(NullPointerException.class, () -> t.addObjectToDb(badObj));
	}
}
