package com.revature.ObjSql;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.revature.Meta.MetaConstructor;
import com.revature.Meta.MetaModel;
import com.revature.annotations.PrimaryKey;

public class Inserter extends Genericer {

	public static final Inserter ObjIns = new Inserter();
	
	private static Logger log = Logger.getLogger(Inserter.class);
	
	private Inserter() {
		super();
	}
	
	public static Inserter getInstance() {
		return ObjIns;
	}
	
	private String getColumns(final Collection<String> getters, final Optional<String> serial_name) {
        return String.join(",",getters.stream()
                    .filter(s -> (!serial_name.isPresent() || !s.equals(serial_name.get())))
                    .toArray(String[]::new));
    }
	
	private void setSerialID(final Object obj, final Optional<Map.Entry<Method,String[]>> setter,final PreparedStatement pstmt) {
 
            ResultSet rs;
			
            try {
            	rs = pstmt.getGeneratedKeys();
            	while (rs.next() && setter.isPresent()) {
				    setter.get().getKey().invoke(obj,rs.getInt(setter.get().getValue()[0]));
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    
    }

	public boolean confirmTable(final Object obj, final Connection conn) {
		ArrayList<String> tables = new ArrayList<String>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT table_name"
					+ " FROM information_schema.TABLES"
					+ " WHERE table_schema = 'public'");
			
			while (rs.next()) {
				tables.add(rs.getString("table_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (!tables.contains(obj.getClass().getSimpleName().toLowerCase())) {
			log.info("Creating table for entity " + obj.getClass().getSimpleName());
			return makeEntity(obj, conn);
		} 
		return true;
	}
	
	
	public boolean saveObject(final Object obj,final Connection conn) {
        
		if(confirmTable(obj, conn)) {
			try {
	            final MetaModel<?> model                           = MetaConstructor.getInstance().getModels().get(obj.getClass().getSimpleName());
	            final HashMap<String,Method> getters               = model.getGetters();
	            final Optional<String> serial_name                 = getSerialName(obj.getClass());
	            final Optional<Map.Entry<Method, String[]>> setter = getSerialKeyEntry(serial_name, model.getSetters());
	            final String args                                  = getArgs((serial_name.isPresent()) ? getters.keySet().size() - 2 : getters.keySet().size() - 1);
	            final String columns                               = getColumns(getters.keySet(), serial_name);
	            final String sql                                   = "INSERT INTO " + model.getEntity() + " ( " + columns + " ) VALUES( " + args + " )";
	            final PreparedStatement pstmt                      = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
	            final ParameterMetaData pd                         = pstmt.getParameterMetaData();
	            int index = 1;
	                      
	            for (Map.Entry<String,Method> getter : getters.entrySet()) {
	                if (!serial_name.isPresent() || !getter.getKey().equals(setter.get().getValue()[0])) {
	                    setStatement(pstmt, pd, getter.getValue(), obj, index++);
	                }
	            }
	            if (pstmt.executeUpdate() != 0) {
	                setSerialID(obj,setter,pstmt);
	            }
	            //also place object inside of cache
	            Cacher.getInstance().putObjInCache(obj);
	          ;
	            return true;
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		}
		
		return false;
    }
	

	public boolean makeEntity(final Object obj, final Connection conn) {
		final MetaModel<?> model = MetaConstructor.getInstance().getModels().get(obj.getClass().getSimpleName());
		final HashMap<String,Method> getters = model.getGetters();
		final Optional<String> serial_name = getSerialName(obj.getClass());
		final String columns[] = getColumns(getters.keySet(), serial_name).split(",");
		
		String sql = "CREATE TABLE " + model.getEntity() + " (";
		
		PrimaryKey pk = obj.getClass().getAnnotation(PrimaryKey.class);
		
		if(pk.isSerial()==true) {
			sql += pk.name() + " SERIAL PRIMARY KEY, ";
		} else {
			sql += pk.name() + " " + typeJavaToSql(obj.getClass().getDeclaredField(pk.name()).getType()) +" PRIMARY KEY, ";
		}
		
		try {
			for (int i = 0; i < columns.length; i++) {
				if (i < columns.length-1) {
					sql += columns[i] + " " + typeJavaToSql(obj.getClass().getDeclaredField(columns[i]).getType()) + ", ";
				} else {
					sql += columns[i] + " " + typeJavaToSql(obj.getClass().getDeclaredField(columns[i]).getType()) + ");";
				}
			}
		} catch (NoSuchFieldException e) {
			log.warn("Error in parsing columns", e);
		}
			
		try {
			final PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.execute();
			return true;
		} catch (SQLException e){
			log.warn("Error in makeEntity", e);
			return false;
		}
	}
	
	public String typeJavaToSql(Class<?> type) {
		
		if(type.equals(byte.class) || type.equals(Byte.class) || type.equals(int.class) || type.equals(Integer.class) || type.equals(short.class)||type.equals(Short.class)) {
			return "INTEGER";
		} else if(type.equals(long.class) || type.equals(Long.class) || type.equals(BigInteger.class) ) {
			return "BIGINT";
		} else if(type.equals(boolean.class) || type.equals(Boolean.class)) {
			return "BOOLEAN";
		} else if(type.equals(char.class) || type.equals(Character.class) || type.equals(String.class)) {
			return "LONGVARCHAR";
		} else if (type.equals(double.class) || type.equals(Double.class) || type.equals(float.class) || type.equals(Float.class) || type.equals(BigDecimal.class))  {
			return "NUMERIC";
		} else {
			return "TEXT";
		}
	}
}
;