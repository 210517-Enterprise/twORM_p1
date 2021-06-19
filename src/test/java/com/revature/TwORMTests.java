package com.revature;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.revature.connection.ConnectionFactory;
import com.revature.demo.Person;
import com.revature.model.Person_Two;
import com.revature.twORM.TwORM;

public class TwORMTests {

	private final TwORM t = TwORM.getInstance();
	private final Connection conn = ConnectionFactory.getInstance().getConnection();
	
	
	@Test
	public void testAddModel() {
		boolean result = t.addClass(Person_Two.class);
		assertTrue(result);
	}
	
	@Test
	public void updateAge() {
		
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
	
	
	@Test public void addObject () {
		Person_Two p4 = new Person_Two("Guillame", 40);
		boolean r = false;
		r = t.addObjectToDb(p4);
		assertTrue(r);
	}
	
	@Test
	public void deleteObject() {
		Person_Two p4 = new Person_Two(4, "Guillame", 40);
		
		boolean r = false;
		r = t.deleteObjectFromDB(p4);
		assertTrue(r);
	}
	
	@Test public void simpleListRetrieve() {
		t.addClass(Person_Two.class);
		Optional<List<Object>> results;
		
		results = t.getListObjectFromDB(Person.class);
		
		assertFalse(results.isEmpty());
	}
	
}
