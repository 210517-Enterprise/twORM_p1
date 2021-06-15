package com.revature.Meta;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;

import com.revature.annotations.Entity;
import com.revature.annotations.Getter;
import com.revature.annotations.PrimaryKey;
import com.revature.annotations.Setter;

public class MetaConstructor {
	private static final MetaConstructor constructor = new MetaConstructor();
	private final HashMap<String, MetaModel<?>> models;
	
	private MetaConstructor() {
		super();
		models = new HashMap();
	}
	
	public static MetaConstructor getInstance() {
		return constructor;
	}
	
	public HashMap<String, MetaModel<?>> getModels() {
        return models;
    }
	
	private String getClassName(final Class<?> clazz) {
        return clazz.getSimpleName();
    }
	
	private Method[] getGetters(final Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(m -> m.getDeclaredAnnotation(Getter.class) != null)
                .toArray(Method[]::new);
    }
	
	private Method[] getSetters(final Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(m -> m.getDeclaredAnnotation(Setter.class) != null)
                .toArray(Method[]::new);
    }
	
	private Constructor<?> getConstructor(final Class<?> clazz) {
        return Arrays.stream(clazz.getConstructors())
                .filter(c -> c.getParameterTypes().length == 0)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
	
	private String getEntityName(final Class<?> clazz) {
        return clazz.getDeclaredAnnotation(Entity.class).name();
    }
	
	private HashMap<Method,String[]> makeSetterMap(final Method[] methods) {
        final HashMap<Method, String[]> map = new HashMap<>();
        for(Method m: methods) {
            final String column = m.getDeclaredAnnotation(Setter.class).name();
            final String type   = m.getParameterTypes()[0].getSimpleName();
            map.put(m,new String[]{column,type});
        }
        return map;
    }
	
	private HashMap<String,Method> makeGetterMap(final Method[] methods) {
        final HashMap<String,Method> map = new HashMap<>();
        Arrays.stream(methods).forEach(m -> map.put(m.getDeclaredAnnotation(Getter.class).name(),m));
        return map;
    }

    private String getPrimaryKeyName(final Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                        .filter(f -> f.getDeclaredAnnotation(PrimaryKey.class) != null)
                        .map(f -> f.getDeclaredAnnotation(PrimaryKey.class).name())
                        .findFirst().get();
    }
    
    public void addModel(final Class<?> clazz) {
        final String class_name                 = getClassName(clazz);
        final HashMap<String,Method> getters    = makeGetterMap(getGetters(clazz));
        final HashMap<Method,String[]> setters  = makeSetterMap(getSetters(clazz));
        final Constructor<?> constructor        = getConstructor(clazz);
        final String table_name                 = getEntityName(clazz);
        final String pk                         = getPrimaryKeyName(clazz);
        models.put(class_name,new MetaModel<>(clazz,getters,setters,constructor,table_name,pk));
    }
	
	
}
