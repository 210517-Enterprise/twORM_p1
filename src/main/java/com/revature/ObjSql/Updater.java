package com.revature.ObjSql;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

import com.revature.Meta.MetaConstructor;
import com.revature.Meta.MetaModel;

public class Updater extends Genericer {
	
	private static final Updater ObjUpd = new Updater();
	
	private static Logger log = Logger.getLogger(Updater.class);
	
	private Updater() {
		super();
	}
	
    public static Updater getInstance() {
        return ObjUpd;
    }

	protected String getColumnsAndValues(final HashMap<String, Method> getters, Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, Method> getter : getters.entrySet()) {
			sb.append(getter.getKey() + " = " + getter.getValue().invoke(obj).toString() + " , ");
		}
		return sb.substring(0, sb.length()-3);
	}

    public boolean updateObject(final Object obj, final Connection conn) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
//		try {
            final MetaModel<?> model                           = MetaConstructor.getInstance().getModels().get(obj.getClass().getSimpleName());
            final HashMap<String,Method> getters               = model.getGetters();
            final String pkValue							   = getters.get(model.getPrimary_key_name()).invoke(obj).toString();
            final String columns                               = getColumnsAndValues(getters, obj);
            final String sql                                   = "UPDATE " + model.getEntity() + " SET " + columns + " WHERE " + model.getPrimary_key_name() + " = " + pkValue + ";";
            System.out.println(sql);
//          final PreparedStatement pstmt                      = conn.prepareStatement(sql);
//			pstmt.executeUpdate();
			return true;
//			
//			/*
//			 * UPDATE THE CACHE SOMEHOW
//			 */
//			
//		} catch (SQLException e) {
//			log.error("Failed to update DB",e);
//			return false;
//		}
    }
}
