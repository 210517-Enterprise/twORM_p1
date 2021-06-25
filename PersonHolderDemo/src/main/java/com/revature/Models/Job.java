package com.revature.Models;

import com.revature.annotations.Column;
import com.revature.annotations.Getter;
import com.revature.annotations.PrimaryKey;
import com.revature.annotations.Setter;

public class Job {

	@PrimaryKey(name="id", isSerial=true)
	private int id;
	@Column(name="job_name")
	private String jobName;
	@Column(name="description")
	private String jobDescription;
	
	public Job() {
		super();
	}

	public Job(String jobName, String jobDescription) {
		super();
		this.jobName = jobName;
		this.jobDescription = jobDescription;
	}
	
	public Job(int id, String jobName, String jobDescription) {
		super();
		this.id = id;
		this.jobName = jobName;
		this.jobDescription = jobDescription;
	}

	@Getter(name="id")
	public int getId() {
		return id;
	}
	
	@Setter(name="id")
	public void setId(int id) {
		this.id = id;
	}

	@Getter(name="job_name")
	public String getJobName() {
		return jobName;
	}

	@Setter(name="job_name")
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	@Getter(name="description")
	public String getJobDescription() {
		return jobDescription;
	}

	@Setter(name="description")
	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}
	
}
