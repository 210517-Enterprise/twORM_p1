package com.revature.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * Annotation to declare an object represents an entity (table)
 * inside a database. name is the name of the table inside the
 * database and will be taken from the class.
 */

	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Entity {
		String name();
	}
