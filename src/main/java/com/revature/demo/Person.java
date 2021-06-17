package com.revature.demo;

import com.revature.annotations.*;

@Entity(name = "person")
public class Person {
	
	@PrimaryKey(name = "id", isSerial = true)
	@SerialPK(name = "id")
	private int id;
	@Column(name = "name")
	private String name;
	@Column(name = "age")
	private int age;
	
	public Person() {
		super();
	}

	public Person(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
	
	public Person(int id, String name, int age) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
	}
	
	@Getter(name = "id")
	public int getId() {
		return id;
	}

	@Setter(name = "id")
	public void setId(int id) {
		this.id = id;
	}

	@Getter(name = "name")
	public String getName() {
		return name;
	}

	@Setter(name = "name")
	public void setName(String name) {
		this.name = name;
	}

	@Getter(name = "age")
	public int getAge() {
		return age;
	}

	@Setter(name = "age")
	public void setAge(int age) {
		this.age = age;
	}
	
}
