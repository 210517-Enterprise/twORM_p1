package com.revature;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.revature.connection.ConnectionFactory;
import com.revature.model.Person_Two;
import com.revature.twORM.TwORM;

public class UpdateTests {
	@BeforeAll
	public static void setup() {
		System.out.println("Beginning tests of Update Service. Creating table to test against.");
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
		System.out.println("Update service tests complete. Yeeting Table.");
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
	public void updateAge() {
		TwORM t = TwORM.getInstance();
		t.addClass(Person_Two.class);
		Person_Two p1 = new Person_Two(1, "Tom", 18);
		
		p1.setAge(26);
		
		boolean r = false;
		
		r = t.updateObjectInDB(p1);
		
		Person_Two p2 = (Person_Two) t.getByPK(Person_Two.class, 1).get();
		
		assertTrue(r);
		assertEquals(p2.getId(), 1);
		assertEquals(p2.getName(), "Tom");
		assertEquals(p2.getAge(), 26);
		
		
	}
	
	@Test
	public void updateName() {
		TwORM t = TwORM.getInstance();
		t.addClass(Person_Two.class);
		boolean r;
		
		Person_Two harry = new Person_Two(3, "Harry", 18);
		harry.setName("Harrison");
		
		r = t.updateObjectInDB(harry);
		
		Optional<List<Object>> res = t.getListByColumn(Person_Two.class, "user_name", "Harrison");
		List<Object> results = res.get();
		Person_Two test = (Person_Two) results.get(0);
		
		assertTrue(r);
		assertEquals(test.getId(), harry.getId());
		assertEquals(test.getName(), harry.getName());
		assertEquals(test.getAge(), harry.getAge());
		
	}
	
	@Test
	public void updateIdDoesNothing() {
		TwORM t = TwORM.getInstance();
		t.addClass(Person_Two.class);
		boolean r;
		
		Person_Two dick = new Person_Two(2, "Dick", 18);
		dick.setId(7);
		r=t.updateObjectInDB(dick);
		
		Optional<List<Object>> res = t.getListByColumn(Person_Two.class, "user_name", "Dick");
		List<Object> results = res.get();
		Person_Two test = (Person_Two) results.get(0);
		
		assertFalse(r);
		assertFalse(test.getId() == dick.getId());
		assertEquals(test.getAge(), dick.getAge());
		assertEquals(test.getName(), dick.getName());
	}
	
	@Test
	public void updateEntryDoesNotExist() {
		TwORM t = TwORM.getInstance();
		Person_Two p = new Person_Two(9, "Pierre", 27);
		assertFalse(t.updateObjectInDB(p));
	}
	
}
