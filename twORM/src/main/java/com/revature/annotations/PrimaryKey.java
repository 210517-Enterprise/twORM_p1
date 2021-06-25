package com.revature.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author A_R_T
 * An annotation that declares a field represent the primary key inside a database.
 * @Param String name(): a string that represents the name of the column inside the table.
 * @Param boolean isSerial: a boolean value to set whether the value should be auto-generated or user-provided
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PrimaryKey {
	String name();
	boolean isSerial();
}