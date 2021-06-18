package com.revature;

import java.lang.reflect.InvocationTargetException;
import com.revature.Meta.MetaConstructor;
import com.revature.ObjSql.Updater;
import com.revature.demo.Person;

public class Driver {

	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Updater u = Updater.getInstance();
		MetaConstructor mc = MetaConstructor.getInstance();
		mc.addModel(Person.class);
		Person p1 = new Person("Billy", 10);
		// testing what the sql statement will be before preparedStatement takes over
		// simply prints the concatenated String
		u.updateObject(p1, null);
		
	}
}
