package com.revature;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

import com.revature.demo.Crime;
import com.revature.demo.Person_Two;
import com.revature.twORM.TwORM;

public class Driver {

	public static void main(String[] args)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		Crime arson = new Crime("Arson", "Burning Buildings", 5);
		Crime assault = new Crime("Assault", "Threatening Violence", 10);
		Crime battery = new Crime("Battery", "Commiting Violent Acts", 15);
		
		final TwORM t = TwORM.getInstance();
		t.disableAutoCommit();
		t.addClass(Crime.class);
		t.setTransactionLevel();
		
		t.addObjectToDb(arson);
		t.addObjectToDb(assault);
		t.commitChanges();
		t.addObjectToDb(battery);
		
		HashMap<Class<?>, HashSet<Object>> cache = t.getCache();
		System.out.println(cache.get(Crime.class));
		System.out.println(t.getByPK(Person_Two.class, "Battery"));
		
		
		}
	}

