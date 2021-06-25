package com.revature.ObjSql;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.revature.Meta.MetaConstructor;
import com.revature.Meta.MetaModel;

public class Updater {

	private static final Updater ObjUpd = new Updater();

	private static Logger log = Logger.getLogger(Updater.class);

	private Updater() {
		super();
	}

	public static Updater getInstance() {
		return ObjUpd;
	}

	protected String getColumns(final HashMap<String, Method> getters) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, Method> getter : getters.entrySet()) {
			sb.append(getter.getKey() + " = ? , ");
		}
		return sb.substring(0, sb.length() - 2);
	}

	public boolean updateObject(final Object obj, final Connection conn)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		try {
			Cacher cache = Cacher.getInstance();
			final MetaModel<?> model = MetaConstructor.getInstance().getModels().get(obj.getClass().getSimpleName());
			final HashMap<String, Method> getters = model.getGetters();
			final String columns = getColumns(getters);
			final String sql = "UPDATE " + model.getEntity() + " SET " + columns + " WHERE " + model.getPrimary_key_name() + " = ? ;";
			final PreparedStatement pstmt = conn.prepareStatement(sql);

			int index = 1;
			for (Map.Entry<String, Method> getter : getters.entrySet()) {
				if (getter.getValue().invoke(obj) != null) {
					pstmt.setObject(index, getter.getValue().invoke(obj));
					index++;
				} else {
					pstmt.setNull(index, Types.NULL);
					index++;
				}
			}
			pstmt.setObject(index, getters.get(model.getPrimary_key_name()).invoke(obj));
			int result = pstmt.executeUpdate();

			if (result > 0) {
				Class<? extends Object> clazz = obj.getClass();
				String[] pkColumn = { model.getPrimary_key_name() };
				String[] pk = { getters.get(model.getPrimary_key_name()).invoke(obj).toString() };
				Optional<List<Object>> op = cache.getObjFromCache(clazz, getters, pkColumn, pk);
				if (op.isPresent()) {
					Object remove = op.get().get(0);
					cache.removeObjFromCache(remove);
				}
				cache.putObjInCache(obj);
				return true;
			} else {
				log.warn("Object not updated. Object does not exist or does not match DB");
				return false;
			}
		} catch (SQLException e) {
			log.error("Failed to update DB", e);
			e.printStackTrace();
			return false;
		}
	}
}
