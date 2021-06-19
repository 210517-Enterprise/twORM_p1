package com.revature.model;

import com.revature.annotations.Column;
import com.revature.annotations.Entity;
import com.revature.annotations.Getter;
import com.revature.annotations.PrimaryKey;
import com.revature.annotations.SerialPK;
import com.revature.annotations.Setter;

@Entity(name = "person_two")
public class Person_Two {
	
	@PrimaryKey(name = "id", isSerial = true)
	@SerialPK(name = "id")
	private int id;
	@Column(name = "user_name")
	private String user_name;
	@Column(name = "user_age")
	private int user_age;
	
	public Person_Two() {
		super();
	}

	public Person_Two(String name, int age) {
		super();
		this.user_name = name;
		this.user_age = age;
	}
	
	public Person_Two(int id, String name, int age) {
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

	@Override
	public String toString() {
		return "Person [id=" + id + ", user_name=" + user_name + ", user_age=" + user_age + "]";
	}
	
}
