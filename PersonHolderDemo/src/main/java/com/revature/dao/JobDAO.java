package com.revature.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.revature.Models.Job;
import com.revature.twORM.TwORM;

public class JobDAO {

	private TwORM t;
	public JobDAO() {
		super();
		t = TwORM.getInstance();
		t.addClass(Job.class);
	}
	
	
	
	public boolean insertJob(Job... jobs) {
		if(jobs.length <= 0) {
			return false;
		}
		t.beginTransaction();
		for (Job j : jobs) {
			if(t.addObjectToDb(j)) {
				continue;
			} else {
				t.abortChanges();
				return false;
			}
		}
		t.commitChanges();
		return true;
		
	}
	
	public boolean updateJob(Job... jobs) {
		if (jobs.length <= 0) {
			return false;
		}
		t.beginTransaction();
		for (Job j : jobs) {
			if (t.updateObjectInDB(j)) {
				continue;
			} else {
				t.abortChanges();
				return false;
			}
		}
		t.commitChanges();
		return true;
	}
	
	public boolean deleteJob(Job... jobs) {
		if (jobs.length <= 0) {
			return false;
		}
		t.beginTransaction();
		for (Job j: jobs) {
			if (t.deleteObjectFromDB(j)) {
				continue;
			} else {
				t.abortChanges();
				return false;
			}
		}
		t.commitChanges();
		return true;
	}
	
	public Job getJobPK(int pk) {
		return (Job) t.getByPK(Job.class, pk).get();
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
	
	public List<Job> getJobByColumn(String column, Object value) {
		List<Job> results = new ArrayList<Job>();
		List<Object> rs = t.getListByColumn(Job.class, column, value).get();
		
		if (rs.isEmpty()) {
			return null;
		}
		rs.forEach((o) -> results.add((Job) o));
		return results;	
	}
	
	public List<Job> getJobByColumns(HashMap<String, Object> columns) {
		List<Job> results = new ArrayList<Job>();
		List<Object> rs = t.getListByColumns(Job.class, columns).get();
		
		if (rs.isEmpty()) {
			return null;
		}
		rs.forEach((o) -> results.add((Job) o));
		return results;	
	}
	
	
}