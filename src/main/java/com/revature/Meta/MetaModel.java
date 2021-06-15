package com.revature.Meta;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;

public class MetaModel<T> {

	private final Class<T> clazz;
	private final HashMap<String, Method> getters;
	private final HashMap<Method,String[]> setters;
	private final Constructor<?> constructor;
	private final String entity;
	private final String primary_key_name;
	
	public MetaModel(Class<T> clazz, HashMap<String, Method> getters, HashMap<Method,String[]> setters, Constructor<?> constructor, String entity, String primary_key_name) {
		this.clazz = clazz;
		this.getters = getters;
		this.setters = setters;
		this.constructor = constructor;
		this.entity = entity;
		this.primary_key_name = primary_key_name;
	}

	public Class<T> getClazz() {
		return clazz;
	}

	public HashMap<String, Method> getGetters() {
		return getters;
	}

	public HashMap<Method, String[]> getSetters() {
		return setters;
	}

	public Constructor<?> getConstructor() {
		return constructor;
	}

	public String getEntity() {
		return entity;
	}

	public String getPrimary_key_name() {
		return primary_key_name;
	}
	
	
}
