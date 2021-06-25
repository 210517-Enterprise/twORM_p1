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
/**
 * An annotation to declare and object represents an entity (table)
 * inside the database. 
 * @author A_R_T
 * @Param String name(): a string that represents the name of table inside the database.
 *
 */
	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Entity {
		String name();
	}
