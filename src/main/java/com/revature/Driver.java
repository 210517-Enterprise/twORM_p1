package com.revature;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;

import com.revature.demo.Person_Two;
import com.revature.twORM.TwORM;

public class Driver {

	public static void main(String[] args)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		final TwORM t = TwORM.getInstance();
		
		t.addClass(Person_Two.class);

		Person_Two p1 = new Person_Two("Tom", 18);
		Person_Two p2 = new Person_Two("Dick", 18);
		Person_Two p3 = new Person_Two("Harry", 18);
		Person_Two p4 = new Person_Two("Guillame", 40);
		boolean r1 = false;
		boolean r2 = false;
		boolean r3 = false;
		boolean r4 = false;
		r1 = t.addObjectToDb(p1);
		r2 = t.addObjectToDb(p2);
		r3 = t.addObjectToDb(p3);
		r4 = t.addObjectToDb(p4);
		System.out.println("Entry States: " + r1 + r2 + r3 + r4);

		p1.setAge(26);
		p2.setAge(21);
		p3.setAge(17);

		r1 = false;
		r2 = false;
		r3 = false;

		r1 = t.updateObjectInDB(p1);
		r2 = t.updateObjectInDB(p2);
		r3 = t.updateObjectInDB(p3);
		System.out.println("Update States: " + r1 + r2 + r3);

		HashMap<Class<?>, HashSet<Object>> cache = t.getCache();

		Object[] contents = cache.get(Person_Two.class).toArray();

		for (int i = 0; i < contents.length; i++) {
			System.out.println(contents[i]);
		}
	}
}
