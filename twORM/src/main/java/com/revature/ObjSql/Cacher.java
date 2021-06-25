package com.revature.ObjSql;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

import org.apache.log4j.Logger;

public class Cacher {

	private static Logger log = Logger.getLogger(Cacher.class);

	private final static Cacher ObjCache = new Cacher();

	private final HashMap<Class<?>, HashSet<Object>> cache;

	private Cacher() {
		super();
		cache = new HashMap<>();
	}

	public static Cacher getInstance() {
		return ObjCache;
	}

	public HashMap<Class<?>, HashSet<Object>> getCache() {
		return cache;
	}

	public void putAllOfEntityInCache(Class<?> clazz, List<Object> objs) {
		HashSet<Object> set = new HashSet<>(objs);

		cache.put(clazz, set);
	}

	public void putObjInCache(final Object obj) {
		if (!cache.containsKey(obj.getClass())) {
			cache.put(obj.getClass(), new HashSet<>());
		}
		cache.get(obj.getClass()).add(obj);
	}

	private boolean compareColumnToConditional(final Object obj, final HashMap<String, Method> getters, final String column, final String value) {
		try {
			return getters.get(column).invoke(obj).toString().equals(value);
		} catch (InvocationTargetException | IllegalAccessException e) {
			log.warn(e);
		}
		return false;
	}

	private boolean compareObjects(final Object obj, final HashMap<String, Method> getters, final String[] columns, final String[] conditions) {
		final Queue<Boolean> values = new LinkedList<>();
		for (int i = 0; i < columns.length; i++) {
			values.add(compareColumnToConditional(obj, getters, columns[i], conditions[i]));
		}
		return !(values.contains(false));
	}

	public Optional<List<Object>> getObjFromCache(final Class<?> clazz, final HashMap<String, Method> getters, final String[] columns, final String[] conditions) {
		if (!cache.containsKey(clazz)) {
			return Optional.empty();
		}
		try {
			final List<Object> list = new LinkedList<>();
			for (Object o : cache.get(clazz)) {
				if (compareObjects(o, getters, columns, conditions)) {
					list.add(o);
				}
			}
			return (list.size() > 0) ? Optional.of(list) : Optional.empty();
		} catch (Exception e) {
			log.warn(e);
		}
		return Optional.empty();
	}

	public void removeObjFromCache(final Object obj) {
		if (cache.containsKey(obj.getClass())) {
			cache.get(obj.getClass()).remove(obj);
		}
	}
}
