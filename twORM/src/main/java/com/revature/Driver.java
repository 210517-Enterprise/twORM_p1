package com.revature;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

import com.revature.demo.Person_Two;
import com.revature.twORM.TwORM;

public class Driver {

	public static void main(String[] args)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		Person_Two tom = new Person_Two("Tom", 28);
		Person_Two james = new Person_Two("James", 33);
		Person_Two brandon = new Person_Two("Brandon", 27);
		
		final TwORM t = TwORM.getInstance();
		t.disableAutoCommit();
		t.addClass(Person_Two.class);
		t.setTransactionLevel();
		
		t.addObjectToDb(tom);
		t.addObjectToDb(james);
		t.commitChanges();
		
		t.addObjectToDb(brandon);
		t.abortChanges();
		
		HashMap<Class<?>, HashSet<Object>> cache = t.getCache();
		System.out.println(cache.get(Person_Two.class));
		
		System.out.println(t.getByPK(Person_Two.class, 1));
		
		
		}
	}

