package com.revature;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.revature.connection.ConnectionFactory;
import com.revature.model.Person_Two;
import com.revature.twORM.TwORM;

public class ReadTests {

	@BeforeAll
	public static void setup() {
		System.out.println("Beginning tests of Delete Service. Creating table to test against.");
		ConnectionFactory cf = ConnectionFactory.getInstance();
		Connection conn = cf.getConnection();
		String sql = "DROP TABLE IF EXISTS person_two;\r\n" + "\r\n" + "CREATE TABLE person_two (\r\n"
				+ "id SERIAL PRIMARY KEY,\r\n" + "user_name VARCHAR(50),\r\n" + "user_age int\r\n" + ");\r\n" + "\r\n"
				+ "INSERT INTO person_two (user_name, user_age) VALUES \r\n" + "	('Tom', 18),\r\n"
				+ "	('Dick', 18),\r\n" + "	('Harry', 18), \r\n ('Harry', 25), \r\n ('Harry', 36);";

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
	public void simpleListRetrieve() {
		TwORM t = TwORM.getInstance();
		t.addClass(Person_Two.class);
		Optional<List<Object>> results;
		List<Object> middle;
		List<Person_Two> resultsList= new ArrayList<>();
		
		results = t.getListObjectFromDB(Person_Two.class);
		
		if (results.isPresent()) {
			middle = results.get();
			middle.forEach((i) -> resultsList.add((Person_Two) i));
		}
		
		
		
		assertTrue(results.isPresent());
		assertTrue(resultsList.size() == 5);
		assertEquals(resultsList.get(3).getName(), "Harry");
	}
	
	
	@Test
	public void getByPKTest() {
		TwORM t = TwORM.getInstance();
		t.addClass(Person_Two.class);
		Optional<Object> rs;
		rs = t.getByPK(Person_Two.class, 2);
		Person_Two result = (Person_Two) rs.get();
		System.out.println(result);
		
		assertTrue(rs != null && rs.isPresent());
		assertTrue(result.getClass() == Person_Two.class);
		assertEquals(result.getId(),2);
		assertEquals(result.getName(), "Dick");
		assertEquals(result.getAge(),18);
		
	}
	
	@Test
	public void getByPKDoesNotExist() {
		TwORM t = TwORM.getInstance();
		t.addClass(Person_Two.class);
		Optional<Object> rs;
		rs = t.getByPK(Person_Two.class, 11);
		assertFalse(rs.isPresent());
	}
	
	@Test
	public void getByColumnTestList() {
		TwORM t = TwORM.getInstance();
		t.addClass(Person_Two.class);
		Optional<List<Object>> results;
		results = t.getListByColumn(Person_Two.class, "user_name", "Harry");
		List<Object> middle = results.get();
		List<Person_Two> resultList= new ArrayList<Person_Two>();
		middle.forEach((i) -> resultList.add((Person_Two)i));
		
		assertTrue(results.isPresent());
		assertTrue(results.get().get(0).getClass() == Person_Two.class);
		assertEquals(resultList.size(), 3);
		assertTrue(resultList.get(0).getAge() == 18 && resultList.get(1).getAge() == 25 && resultList.get(2).getAge() == 36);
	}
	
	@Test 
	public void getByColumnTestSingleResult() {
		TwORM t = TwORM.getInstance();
		t.addClass(Person_Two.class);
		Optional<List<Object>> results;
		results = t.getListByColumn(Person_Two.class, "user_name", "Tom");
		List<Object> middle = results.get();
		List<Person_Two> resultList= new ArrayList<Person_Two>();
		middle.forEach((i) -> resultList.add((Person_Two)i));
		
		assertTrue(results.isPresent());
		assertTrue(results.get().get(0).getClass() == Person_Two.class);
		assertEquals(resultList.size(), 1);
		assertEquals(resultList.get(0).getName(), "Tom");
	}
	
	@Test
	public void getByColumnColumnNotExist() {
		TwORM t = TwORM.getInstance();
		t.addClass(Person_Two.class);
		Optional<List<Object>> results;
		results = t.getListByColumn(Person_Two.class, "u_name", "Tom");
		assertFalse(results.isPresent());
	}
	
	@Test
	public void getByColumnValueBad() {
		TwORM t = TwORM.getInstance();
		t.addClass(Person_Two.class);
		Optional<List<Object>> results;
		results = t.getListByColumn(Person_Two.class, "user_name", "Bob");
		assertFalse(results.isPresent());
	}
	
	
	@Test
	public void getByColumnsTest() {
		TwORM t = TwORM.getInstance();
		t.addClass(Person_Two.class);
		Optional<List<Object>> results;
		HashMap<String, Object> columns = new HashMap<>();
		columns.put("user_name", "Harry");
		columns.put("user_age", 36);
		results = t.getListByColumns(Person_Two.class, columns);
		List<Object> middle = results.get();
		List<Person_Two> resultList= new ArrayList<Person_Two>();
		middle.forEach((i) -> resultList.add((Person_Two)i));
		assertTrue(results.isPresent());
		assertTrue(results.get().get(0).getClass() == Person_Two.class);
		assertEquals(resultList.size(),1);
		assertEquals(resultList.get(0).getId(), 5);
		assertEquals(resultList.get(0).getAge(), 36);
	}
	
	@Test
	public void getByColumnsNotExist() {
		TwORM t = TwORM.getInstance();
		t.addClass(Person_Two.class);
		Optional<List<Object>> results;
		HashMap<String, Object> columns = new HashMap<>();
		columns.put("u_name", "Harry");
		columns.put("user_age", 36);
		results = t.getListByColumns(Person_Two.class, columns);
		assertFalse(results.isPresent());
	}
	
	@Test
	public void getByColumnsBadValue() {
		TwORM t = TwORM.getInstance();
		t.addClass(Person_Two.class);
		Optional<List<Object>> results;
		HashMap<String, Object> columns = new HashMap<>();
		columns.put("user_name", "Harry");
		columns.put("user_age", 10);
		results = t.getListByColumns(Person_Two.class, columns);
		assertFalse(results.isPresent());
	}
	
	
}
