package com.revature.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * Annotation declares that a field represents the foreign key
 * in a table in the database.
 * name is the name of the column inside the table.
 */

/**
 * An annotation used for future functionality not yet implemented. Do not use.
 * @author A_R_T
 * @Param String name(): .
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ForeignKey {
	String name();
}
