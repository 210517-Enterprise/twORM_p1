package com.revature.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.revature.Models.Job;
import com.revature.twORM.TwORM;

public class PersonDAO {

	private TwORM t;
	public PersonDAO() {
		super();
		t = TwORM.getInstance();
		t.addClass(Job.class);
	}
	
	
	
	public boolean insertPerson(Job... persons) {
		if(persons.length <= 0) {
			return false;
		}
		t.beginTransaction();
		for (Job p : persons) {
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
	
	public boolean updatePerson(Job... persons) {
		if (persons.length <= 0) {
			return false;
		}
		t.beginTransaction();
		for (Job p : persons) {
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
	
	public boolean deletePerson(Job... persons) {
		if (persons.length <= 0) {
			return false;
		}
		t.beginTransaction();
		for (Job p: persons) {
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
	
	public Job getPersonPK(String username) {
		return (Job) t.getByPK(Job.class, username).get();
	}
	
	public List<Job> getAll() {
		List<Job> results = new ArrayList<Job>();
		List<Object> rs = t.getListObjectFromDB(Job.class).get();
		
		if (rs.isEmpty()) {
			return null;
		}
		rs.forEach((o) -> results.add((Job) o));
		return results;
	}
	
	public List<Job> getPersonByColumn(String column, Object value) {
		List<Job> results = new ArrayList<Job>();
		List<Object> rs = t.getListByColumn(Job.class, column, value).get();
		
		if (rs.isEmpty()) {
			return null;
		}
		rs.forEach((o) -> results.add((Job) o));
		return results;	
	}
	
	public List<Job> getPersonByColumns(HashMap<String, Object> columns) {
		List<Job> results = new ArrayList<Job>();
		List<Object> rs = t.getListByColumns(Job.class, columns).get();
		
		if (rs.isEmpty()) {
			return null;
		}
		rs.forEach((o) -> results.add((Job) o));
		return results;	
	}
	
	
}
