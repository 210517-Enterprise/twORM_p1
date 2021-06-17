package com.revature;

import com.revature.demo.Person;

import java.sql.Connection;

import com.revature.Meta.MetaConstructor;
import com.revature.ObjSql.Inserter;
import com.revature.connection.*;

public class testDriver {

	public static void main(String[] args) {
		
		Person p = new Person("Billy", 10);
		
		Inserter i = Inserter.getInstance();
		
		MetaConstructor mc = MetaConstructor.getInstance();
		mc.addModel(Person.class);
		
		Connection c = ConnectionFactory.getInstance().getConnection();
		
		System.out.println(i.makeObject(p, c));
		System.out.println(i.saveObject(p, c));
	}
		
}
