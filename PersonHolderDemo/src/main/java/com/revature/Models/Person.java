package com.revature.Models;

import com.revature.annotations.*;

@Entity(name="person")
public class Person {

	@PrimaryKey(name="id", isSerial=true)
	private int id;
	@Column(name="first_name")
	private String firstName;
	@Column(name="last_name")
	private String lastName;
	@Column(name="job")
	private String Job;
	@Column(name="age")
	private int age;
	@Column(name="salary")
	private double salary;
	
	public Person() {
		super();
	}

	public Person(int id, String firstName, String lastName, String job, int age, double salary) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		Job = job;
		this.age = age;
		this.salary = salary;
	}
	
	public Person(String firstName, String lastName, String job, int age, double salary) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		Job = job;
		this.age = age;
		this.salary = salary;
	}

	@Getter(name="id")
	public int getId() {
		return id;
	}

	@Setter(name="id")
	public void setId(int id) {
		this.id = id;
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

	@Getter(name="job")
	public String getJob() {
		return Job;
	}

	@Setter(name="job")
	public void setJob(String job) {
		Job = job;
	}

	@Getter(name="age")
	public int getAge() {
		return age;
	}

	@Setter(name="age")
	public void setAge(int age) {
		this.age = age;
	}

	@Getter(name="salary")
	public double getSalary() {
		return salary;
	}

	@Setter(name="salary")
	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	
	
	
}
