package com.revature.ObjSql;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
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

import com.revature.Meta.MetaConstructor;
import com.revature.Meta.MetaModel;

public class Inserter {

	public static final Inserter ObjIns = new Inserter();
	
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
	
	public boolean saveObject(final Object obj,final Connection conn) {
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
            //also place object inside of cache eventually.
          ;
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
	
	public boolean confirmTable(String entityName, final Connection conn) {
		ArrayList<String> tables = new ArrayList<String>();
		try {
			DatabaseMetaData meta = conn.getMetaData();
			ResultSet rs = meta.getTables(null, null, null, new String[] {"Table"});
			
			while (rs.next()) {
				tables.add(rs.getString("TABLE_NAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (tables.contains(entityName)) {
			return true;
		}
		return false;
	}
	
	
	
	
	
	
}
