package com.revature.Models;

public class Person {

	int id;
	String firstName;
	String lastName;
	String Job;
	int age;
	double salary;
	
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getJob() {
		return Job;
	}

	public void setJob(String job) {
		Job = job;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	
	
	
}
