package com.revature.ObjSql;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.apache.log4j.Logger;

import com.revature.Meta.MetaConstructor;
import com.revature.Meta.MetaModel;

public class Retriever extends Genericer {

	private static final Retriever ObjRet = new Retriever();
	
	private static Logger log = Logger.getLogger(Retriever.class);
	
	private Retriever() {
		super();
	}
	
    public static Retriever getInstance() {
        return ObjRet;
    }
    
    public Optional<List<Object>> getAllEntity(Class<?> clazz,  Connection c) {
    	
    	String sql = "SELECT * FROM " + clazz.getSimpleName();
    	try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			return resultSetToList(rs, clazz);
			
		} catch (SQLException e) {
			log.error("Failed to get all entities for " + clazz.getSimpleName(), e);
		}
    	
    	return Optional.empty();
    }

    private Optional<List<Object>> resultSetToList(ResultSet rs, Class<?> clazz) {
    	
    	try {
    		List<Object> res = new ArrayList<>();
    		MetaModel<?> model = MetaConstructor.getInstance().getModels().get(clazz.getSimpleName());
    		
    		// Iterate over all results
    		while(rs.next()) {
    			Object obj = model.getConstructor().newInstance();
    			
    			// Get Setters of the model
    			Set<Map.Entry<Method, String[]>> setters = model.getSetters().entrySet();
    			
    			// For each setter convert the SQL type to Java
    			for(Map.Entry<Method, String[]> s : setters) {
    				setField(obj, s.getKey(), rs, s.getValue());
    			}
    			
    			// Add object
    			res.add(obj);
    		}
    		
    		if(res.isEmpty()) {
    			return Optional.empty();
    		}
    		
    		return Optional.of(res);
    		
    	} catch (Exception e) {
    		log.error("Error in converting ResultSet to List", e);
    	}
    	
    	return Optional.empty();
    }
 
    private void setField(Object obj, Method method, ResultSet rs, String[] fields) {
    	String type = fields[1];
	    // fields contains {<column name>, <simple type name>}
    	// TODO add more types
    	try {
    		if(type.equals("String")) {
	    		method.invoke(obj, rs.getString(fields[0]));
	    	} else if (type.equals("int")){
	    		method.invoke(obj, rs.getInt(fields[0]));
	    	} else if (type.equals("double")) {
	    		method.invoke(obj, rs.getDouble(fields[0]));
	    	} else if (type.equals("BigDecimal")) {
	    		method.invoke(obj, rs.getBigDecimal(fields[0]));
	    	}
	    } catch (Exception e) {
	    	log.error("Error in setting fields when retrieving", e);
	    }
    }
    
}
