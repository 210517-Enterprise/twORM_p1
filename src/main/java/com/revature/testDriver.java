package com.revature;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

import com.revature.Meta.MetaConstructor;
import com.revature.ObjSql.Retriever;
import com.revature.ObjSql.Transacter;
import com.revature.ObjSql.Updater;
import com.revature.connection.ConnectionFactory;
import com.revature.demo.Person;
import com.revature.twORM.TwORM;

public class testDriver {

	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		Person p1 = new Person (1, "Billy", 10);
		
		TwORM tworm = TwORM.getInstance();
		
		Optional<List<Object>> rs = tworm.getListObjectFromDB(Person.class);
		System.out.println(rs);
		
		
		
		
	}
		
}
