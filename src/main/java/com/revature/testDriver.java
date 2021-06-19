package com.revature;

import java.sql.Connection;
import java.util.List;

import com.revature.Meta.MetaConstructor;
import com.revature.ObjSql.Inserter;
import com.revature.ObjSql.Retriever;
import com.revature.connection.ConnectionFactory;
import com.revature.demo.Person;

public class testDriver {

	public static void main(String[] args) {
		
		Person p1 = new Person("Billy", 10);
		Person p2 = new Person("Jan", 24);
		Person p3 = new Person("Quincy", 177);
		
		Inserter i = Inserter.getInstance();
		
		Connection c = ConnectionFactory.getInstance().getConnection();
		
		MetaConstructor mc = MetaConstructor.getInstance();
		mc.addModel(Person.class);
		i.saveObject(p1, c);
		i.saveObject(p2, c);
		i.saveObject(p3, c);
		Retriever r = Retriever.getInstance();
		List<Object> l = r.getAllEntity(Person.class, c).get();
		
		l.forEach(u -> System.out.println(u));
	
		
	}
		
}
