package com.revature;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.revature.connection.ConnectionFactory;
import com.revature.model.NoAnnotations;
import com.revature.model.Person_Two;
import com.revature.twORM.TwORM;

@TestMethodOrder(OrderAnnotation.class)
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
	@Order(1)
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
	
	@Test 
	@Order(2)
	public void addObject () {
		TwORM t = TwORM.getInstance();
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
		NoAnnotations badObj = new NoAnnotations();
		assertThrows(NullPointerException.class, () -> t.addObjectToDb(badObj));
	}
	
	@Test
	@Order(3)
	public void updateAge() {
		TwORM t = TwORM.getInstance();
		t.addClass(Person_Two.class);
		Person_Two p1 = new Person_Two(1, "Tom", 18);
		Person_Two p2 = new Person_Two(2, "Dick", 18);
		Person_Two p3 = new Person_Two(3, "Harry", 18);
		
		p1.setAge(26);
		p2.setAge(21);
		p3.setAge(17);
		
		boolean r1 = false;
		boolean r2 = false;
		boolean r3 = false;
		
		r1 = t.updateObjectInDB(p1);
		r2 = t.updateObjectInDB(p2);
		r3 = t.updateObjectInDB(p3);
		
		assertTrue(r1 && r2 && r3);
		
		
	}
	
	@Test
	public void updatesPersistToDb () {
		TwORM t = TwORM.getInstance();
		List<Object> rs = t.getListObjectFromDB(Person_Two.class).get();
		assertTrue(rs.size() >=3);
		Person_Two obj1 = (Person_Two) rs.get(0);
		int age =obj1.getAge();
		assertEquals(age, 26);
		
	}
	
	@Test
	public void updateEntryDoesNotExist() {
		TwORM t = TwORM.getInstance();
		Person_Two p = new Person_Two(9, "Pierre", 27);
		assertFalse(t.updateObjectInDB(p));
	}
	
	
	@Test
	public void deleteObject() {
		TwORM t = TwORM.getInstance();
		Person_Two p4 = new Person_Two(4, "Guillame", 40);
		
		boolean r = false;
		r = t.deleteObjectFromDB(p4);
		assertTrue(r);
	}
	
	@Test
	public void deleteObjectNotInDB() {
		TwORM t = TwORM.getInstance();
		
		Person_Two p = new Person_Two(7, "Pierre", 31);
		boolean r = true;
		r = t.deleteObjectFromDB(p);
		assertFalse(r);
	}
	
	@Test
	public void getCacheReturnsCacheOfObjects() {
		TwORM t = TwORM.getInstance();
		
		HashMap<Class<?>, HashSet<Object>> cache = t.getCache();
		
		assertFalse(cache.isEmpty());
		assertTrue(cache.containsKey(Person_Two.class));
	}
	
	@Test 
	public void simpleListRetrieve() {
		TwORM t = TwORM.getInstance();
		t.addClass(Person_Two.class);
		Optional<List<Object>> results;
		
		results = t.getListObjectFromDB(Person_Two.class);
		
		assertFalse(results.isEmpty());
	}
	
	@Test
	public void getByPKTest() {
		TwORM t = TwORM.getInstance();
		Optional<Object> result;
		result = t.getByPK(Person_Two.class, 1);
		
		assertTrue(result != null && !result.isEmpty());
	}
	
	@Test
	public void getByColumnTest() {
		TwORM t = TwORM.getInstance();
		Optional<List<Object>> results;
		results = t.getListByColumn(Person_Two.class, "user_name", "Tom");
		
		assertFalse(results.isEmpty());
	}
	
}
