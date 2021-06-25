package com.revature.Models;

import com.revature.annotations.*;

@Entity(name="person")
public class Person {

	private String job;
	
	@PrimaryKey(name="user_name", isSerial=false)
	private String username;
	@Column(name="first_name")
	private String firstName;
	@Column(name="last_name")
	private String lastName;
	@Column(name="job_id")
	private int jobId;
	@Column(name="user_age")
	private int age;
	@Column(name="salary")
	private int salary;
	
	public Person() {
		super();
	}

	public Person(String username, String firstName, String lastName, int jobid, int age, int salary) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.jobId = jobid;
		this.age = age;
		this.salary = salary;
	}
	

	@Getter(name="user_name")
	public String getId() {
		return username;
	}

	@Setter(name="user_name")
	public void setId(String id) {
		this.username = id;
	}

	@Getter(name="first_name")
	public String getFirstName() {
		return firstName;
	}

	@Setter(name="first_name")
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Getter(name="last_name")
	public String getLastName() {
		return lastName;
	}

	@Setter(name="last_name")
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	@Getter(name="job_id")
	public int getJobId() {
		return jobId;
	}
	
	@Setter(name="job_id")
	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	@Getter(name="user_age")
	public int getAge() {
		return age;
	}

	@Setter(name="user_age")
	public void setAge(int age) {
		this.age = age;
	}

	@Getter(name="salary")
	public int getSalary() {
		return salary;
	}

	@Setter(name="salary")
	public void setSalary(int salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Person [username=" + username + ", firstName=" + firstName + ", lastName=" + lastName + ", jobId="
				+ jobId + ", age=" + age + ", salary=" + salary + "]";
	}
	
	

	
	
}
