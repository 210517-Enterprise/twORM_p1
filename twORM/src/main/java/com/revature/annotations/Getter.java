package com.revature.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * Annotation for a method that gets a value stored
 * in the database.
 * name is the name of the column inside the databse.
 */
/**
 * An annotation that indicates a method that is used to retrieve a value of an object that is stored in a field in the database.
 * @author A_R_T
 * @Param String name(): a string that represents the name of the column inside the table.
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Getter {
	String name();
}