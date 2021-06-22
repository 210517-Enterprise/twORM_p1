package com.revature;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import com.revature.demo.Person_Two;
import com.revature.twORM.TwORM;

public class Driver {

	public static void main(String[] args)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		final TwORM t = TwORM.getInstance();
		
		t.addClass(Person_Two.class);

		Optional<Object> result;
		result = t.getByPK(Person_Two.class, 1);
		
		System.out.println(result);
		}
	}

