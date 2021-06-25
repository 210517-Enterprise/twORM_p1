package com.revature.ObjSql;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.revature.Meta.MetaConstructor;
import com.revature.Meta.MetaModel;

public class Deleter {

	private static final Deleter ObjDel = new Deleter();

	private static Logger log = Logger.getLogger(Deleter.class);

	private Deleter() {
		super();
	}

	public static Deleter getInstance() {
		return ObjDel;
	}

	private static Method getGetter(final String pk, final HashMap<String, Method> getters) {
		return getters.get(pk);

	}

	public boolean removeObjectFromDB(final Object obj, final Connection conn) {
		try {

			final MetaModel<?> model = MetaConstructor.getInstance().getModel(obj);
			final String primary_key = model.getPrimary_key_name();
			final Method getter = getGetter(primary_key, model.getGetters());
			final String sql = "DELETE from " + model.getEntity() + " WHERE " + primary_key + " = ? ";
			final PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setObject(1, getter.invoke(obj, (Object[]) null));

			int result = pstmt.executeUpdate();

			if (result > 0) {

				Cacher.getInstance().removeObjFromCache(obj);

				log.info(model.getEntity() + " with PK of " + primary_key + " has been removed from the DB");

				return true;

			} else {
				log.warn("Object not deleted. Object does not exist or does not match DB");
				return false;
			}
		} catch (SQLException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			log.error("Error in removing object", e);
		}
		return false;
	}

}
