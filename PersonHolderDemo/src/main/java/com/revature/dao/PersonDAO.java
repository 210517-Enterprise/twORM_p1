package com.revature.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.revature.Models.Person;
import com.revature.twORM.TwORM;

public class PersonDAO {

	private TwORM t;
	public PersonDAO() {
		super();
		t = TwORM.getInstance();
		t.addClass(Person.class);
	}
	
	
	
	public boolean insertPerson(Person... persons) {
		if(persons.length <= 0) {
			return false;
		}
		t.beginTransaction();
		for (Person p : persons) {
			if(t.addObjectToDb(p)) {
				continue;
			} else {
				t.abortChanges();
				return false;
			}
		}
		t.commitChanges();
		return true;
		
	}
	
	public boolean updatePerson(Person... persons) {
		if (persons.length <= 0) {
			return false;
		}
		t.beginTransaction();
		for (Person p : persons) {
			if (t.updateObjectInDB(p)) {
				continue;
			} else {
				t.abortChanges();
				return false;
			}
		}
		t.commitChanges();
		return true;
	}
	
	public boolean deletePerson(Person... persons) {
		if (persons.length <= 0) {
			return false;
		}
		t.beginTransaction();
		for (Person p: persons) {
			if (t.deleteObjectFromDB(p)) {
				continue;
			} else {
				t.abortChanges();
				return false;
			}
		}
		t.commitChanges();
		return true;
	}
	
	public Person getPersonPK(String username) {
		return (Person) t.getByPK(Person.class, username).get();
	}
	
	public List<Person> getAll() {
		List<Person> results = new ArrayList<Person>();
		List<Object> rs = t.getListObjectFromDB(Person.class).get();
		
		if (rs.isEmpty()) {
			return null;
		}
		rs.forEach((o) -> results.add((Person) o));
		return results;
	}
	
	public List<Person> getPersonByColumn(String column, Object value) {
		List<Person> results = new ArrayList<Person>();
		List<Object> rs = t.getListByColumn(Person.class, column, value).get();
		
		if (rs.isEmpty()) {
			return null;
		}
		rs.forEach((o) -> results.add((Person) o));
		return results;	
	}
	
	public List<Person> getPersonByColumns(HashMap<String, Object> columns) {
		List<Person> results = new ArrayList<Person>();
		List<Object> rs = t.getListByColumns(Person.class, columns).get();
		
		if (rs.isEmpty()) {
			return null;
		}
		rs.forEach((o) -> results.add((Person) o));
		return results;	
	}
	
	
}
