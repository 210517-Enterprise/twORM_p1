package com.revature;

import com.revature.demo.Person;

import java.sql.Connection;

import com.revature.Meta.MetaConstructor;
import com.revature.ObjSql.Inserter;
import com.revature.connection.*;

public class testDriver {

	public static void main(String[] args) {
		
		Person p1 = new Person("Billy", 10);
		Person p2 = new Person("Jan", 24);
		Person p3 = new Person("Quincy", 177);
		
		Inserter i = Inserter.getInstance();
		
		MetaConstructor mc = MetaConstructor.getInstance();
		mc.addModel(Person.class);
		
		Connection c = ConnectionFactory.getInstance().getConnection();

		i.saveObject(p1, c);
		i.saveObject(p2, c);
		i.saveObject(p3, c);
	}
		
}
