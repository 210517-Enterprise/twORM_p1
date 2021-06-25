package com.revature.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

	/*
	 * Annotation to declare a matching column in the database. 
	 * name is the name inside the table for the column
	 */
	/**
	 * An optional annotation to declare a field corresponds to a column inside the database.
	 * Future functionality may depend on this annotation. 
	 * @author A_R_T
	 * @Param String name(): a string that represents the name of the column inside the table.
	 *
	 */
	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Column {
		String name();
	}
