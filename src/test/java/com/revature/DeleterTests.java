package com.revature;

import java.sql.Connection;

import org.junit.Before;
import org.junit.Test;

import com.revature.ObjSql.Deleter;
import com.revature.model.Person;
import com.revature.twORM.TwORM;

public class DeleterTests {

	private final Deleter deleter = Deleter.getInstance();
	private final TwORM tworm = TwORM.getInstance();
	
	
	@Before
	public void setup() {
		Connection conn = null;
		Person bob = new Person(1, "bob", 25);
		tworm.addClass(Person.class);
	}
	
	
}
