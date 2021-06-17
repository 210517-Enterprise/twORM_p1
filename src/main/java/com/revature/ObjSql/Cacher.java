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

public class Cacher extends Genericer {
	
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

    /**
     *
     * @return HashMap cache of objects. key = class, value = HashSet of all objects of that class.
     */
    public HashMap<Class<?>,HashSet<Object>> getCache() {
        return cache;
    }

    /**
     * Places an object into the cache.
     * @param obj object to place inside of cache.
     */
    public void putObjInCache(final Object obj) {
        if(!cache.containsKey(obj.getClass())) {
            cache.put(obj.getClass(),new HashSet<>());
        }
        cache.get(obj.getClass()).add(obj);
    }

    /**
     * Compares value in value string to that returned by the getter in that object.
     * @param obj object comparing.
     * @param getters HashMap of annotated getters in that object.
     * @param column  String of column name.
     * @param value String of value which the getter should return.
     * @return
     */
    private boolean compareColumnToConditional(final Object obj,final HashMap<String,Method> getters,final String column,final String value) {
        try {
            return getters.get(column).invoke(obj).toString().equals(value);
        }catch(InvocationTargetException | IllegalAccessException e) {
            log.warn(e);
        }
        return false;
    }

    /**
     * Goes through the list of values and compares them using the supplied operators.
     * @param values list of boolean values.
     * @param operators string[] of operators (AND/OR) to apply to the values.
     * @return boolean indicating whether the list matches the operators.
     */
    private boolean compareValuesOfOperators(final Queue<Boolean> values,final String[] operators) {
        if (values.size() > 1) {
            boolean value = false;
            for (String o : operators) {
                value = (o.equals("AND")) ? values.remove() && values.remove() : values.remove() || values.remove();
            }
            return value;
        }
        return values.remove();
    }

    /**
     * Compare object inside of cache with the conditions provided to determine if that object already exists in cache.
     * @param obj Object from cache to check if it matches given conditions.
     * @param getters HashMap of getters inside of object.
     * @param columns String[] of columns to check for comparison.
     * @param conditions String[] values which the checked columns should match.
     * @param operators String[] operators to apply to the columns.
     * @return boolean to indicate a match or not.
     */
    private boolean compareObjects(final Object obj,final HashMap<String,Method> getters,final String[] columns,final String[] conditions,final String[] operators) {
        final Queue<Boolean> values = new LinkedList<>();
        for(int i = 0; i < columns.length; i++) {
            values.add(compareColumnToConditional(obj,getters,columns[i],conditions[i]));
        }
        return compareValuesOfOperators(values,operators);
    }

    /**
     * Method checks if object or objects matching criteria already exists in database.
     * If match is found, it is added to a list. that list is then returned inside of an optional.
     * If no match is found then an empty optional is returned.
     * @param clazz class of object to check for.
     * @param getters HashMap of annotated getters inside of class.
     * @param columns String[] of columns to check for.
     * @param conditions String[] of values that the columns should match
     * @param operators String[] of operators(AND/OR) to apply to columns.
     * @return Optional containing List of objects which match the conditions provided.
     */
    public Optional<List<Object>> getObjFromCache(final Class<?> clazz,final HashMap<String,Method> getters,final String[] columns,final String[] conditions,final String[] operators) {
        if(!cache.containsKey(clazz)) {
            return Optional.empty();
        }
        try {
            final List<Object> list = new LinkedList<>();
            for(Object o: cache.get(clazz)){
                if(compareObjects(o,getters,columns,conditions,operators)) {
                    list.add(o);
                }
            }
            return (list.size() > 0)? Optional.of(list) : Optional.empty();
        }catch(Exception e) {
            log.warn(e);
        }
        return Optional.empty();
    }

    /**
     * Remove an object from cache.
     * @param obj object to remove from cache.
     */
    public void removeObjFromCache(final Object obj) {
        if(cache.containsKey(obj.getClass())) {
            cache.get(obj.getClass()).remove(obj);
        }
    }
}
