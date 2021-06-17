package com.revature.demo;

import com.revature.annotations.*;

@Entity(name = "person")
public class Person {
	
	@PrimaryKey(name = "id", isSerial = true)
	@SerialPK(name = "id")
	private int id;
	@Column(name = "user_name")
	private String user_name;
	@Column(name = "user_age")
	private int user_age;
	
	public Person() {
		super();
	}

	public Person(String name, int age) {
		super();
		this.user_name = name;
		this.user_age = age;
	}
	
	public Person(int id, String name, int age) {
		super();
		this.id = id;
		this.user_name = name;
		this.user_age = age;
	}
	
	@Getter(name = "id")
	public int getId() {
		return id;
	}

	@Setter(name = "id")
	public void setId(int id) {
		this.id = id;
	}

	@Getter(name = "user_name")
	public String getName() {
		return user_name;
	}

	@Setter(name = "user_name")
	public void setName(String name) {
		this.user_name = name;
	}

	@Getter(name = "user_age")
	public int getAge() {
		return user_age;
	}

	@Setter(name = "user_age")
	public void setAge(int age) {
		this.user_age = age;
	}
	
}
