package com.revature.ObjSql;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

	public Optional<List<Object>> getAllEntity(Class<?> clazz, Connection c) {

		String sql = "SELECT * FROM " + clazz.getSimpleName();
		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			List<Object> res = resultSetToList(rs, clazz);

			if (!res.isEmpty()) {
				return Optional.of(res);
			}

		} catch (SQLException e) {
			log.error("Failed to get all entities for " + clazz.getSimpleName(), e);
		}

		return Optional.empty();
	}

	/*
	 * LOOK AT ME... This is an overly verbose way of doing exactly the above
	 */
	public Optional<List<Object>> retrieveObject(Object obj, Connection c) {

		String sql = "SELECT * FROM " + obj.getClass().getSimpleName();

		try {
			MetaModel<?> model = MetaConstructor.getInstance().getModel(obj);

			// Get columns and strip the primary key
			HashMap<String, Method> getters = model.getGetters();
			getters.remove(model.getPrimary_key_name());

			for (int i = 0; i < getters.keySet().size(); i++) {
				if (i == 0) {
					sql += " WHERE ";
				} else {
					sql += " AND ";
				}

				sql += "? = ?";
			}

			PreparedStatement stmt = c.prepareStatement(sql);

		} catch (Exception e) {
			log.error("Error in finding object of Entity " + obj.getClass().getSimpleName(), e);
		}

		return Optional.empty();
	}

	public Optional<Object> retrieveObjectByPK(Class<?> clazz, Object primaryKey, Connection c) {
		try {
			Constructor<?> cons 					= clazz.getConstructor(clazz);
			Optional<Object> obj 					= Optional.of(cons.newInstance(primaryKey));
			final MetaModel<?> model 				= MetaConstructor.getInstance().getModel(obj);
			final HashMap<String, Method> getters 	= model.getGetters();
			final Set<Map.Entry<Method, String[]>> setters = model.getSetters().entrySet();
			
			String[] pkColumn = { model.getPrimary_key_name() };
			String[] pk = { getters.get(model.getPrimary_key_name()).invoke(obj).toString() };
			String[] conditions = {};
			obj = Optional.of(Cacher.getInstance().getObjFromCache(clazz, getters, pkColumn, pk, conditions).get());
			if (obj.isPresent())
				return obj;
			else {
				String sql = "SELECT * FROM " + clazz.getSimpleName() + " WHERE ";

				// MetaModel<?> model =
				// MetaConstructor.getInstance().getModels().get(clazz.getSimpleName());

				sql += model.getPrimary_key_name() + " = ?;";

				PreparedStatement stmt = c.prepareStatement(sql);

				stmt.setObject(1, primaryKey);

				ResultSet rs = stmt.executeQuery();

				List<Object> res = resultSetToList(rs, model.getClazz());

				if (!res.isEmpty()) {
					return Optional.of(res.get(0));
				}
			}

		} catch (Exception e) {
			log.error("Error in retrieving by PK", e);
		}

		return Optional.empty();
	}

	public Optional<List<Object>> retrieveByColumn(Class<?> clazz, String column, Object value, Connection c) {
		String sql = "SELECT * FROM " + clazz.getSimpleName() + " WHERE ";
		try {
			MetaModel<?> model = MetaConstructor.getInstance().getModels().get(clazz.getSimpleName());
			Set<Map.Entry<Method, String[]>> setters = model.getSetters().entrySet();

			sql += column + " = ?;";

			PreparedStatement stmt = c.prepareStatement(sql);

			stmt.setObject(1, value);

			ResultSet rs = stmt.executeQuery();

			List<Object> res = resultSetToList(rs, model.getClazz());

			if (!res.isEmpty()) {
				return Optional.of(res);
			}

		} catch (Exception e) {
			log.error("Error in retrieving by column", e);

		}
		
		return Optional.empty();
	}
	
	public Optional<List<Object>> retrieveByColumns(Class<?> clazz, HashMap<String, Object> columns, Connection c){
		String sql = "SELECT * FROM " + clazz.getSimpleName() + " WHERE ";
		try {
			MetaModel<?> model = MetaConstructor.getInstance().getModels().get(clazz.getSimpleName());
			Set<Map.Entry<Method, String[]>> setters = model.getSetters().entrySet();
			
			List<Object> values = new ArrayList<>();
			for(String column : columns.keySet()) {
				sql += column + " = ? AND ";
				values.add(columns.get(column));
			}
			
			PreparedStatement stmt = c.prepareStatement(sql.substring(0, sql.length() - 5));
			
			for(int i = 1; i <= values.size(); i++) {
				stmt.setObject(i, values.get(i - 1));
			}
			
			ResultSet rs = stmt.executeQuery();
			
			List<Object> res = resultSetToList(rs, model.getClazz());
			
			if (!res.isEmpty()) {
				return Optional.of(res);
			}
			
		} catch (Exception e) {
			log.error("Error in retrieving by multiple columns", e);

		}

		return Optional.empty();
	}

	public void databaseToCache(Connection c) {
		HashMap<String, MetaModel<?>> models = MetaConstructor.getInstance().getModels();
		Cacher cache = Cacher.getInstance();
				
		models.forEach((modelName, meta) -> {
			String sql = "SELECT * FROM " + meta.getEntity();
			
			try {
				Statement stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
							
				List<Object> objs = resultSetToList(rs, meta.getClazz());

				cache.putAllOfEntityInCache(meta.getClazz(), objs);
				
			} catch (SQLException e) {
				log.warn("SQL error when attempting to cache DB", e);
			}
			
		});
		
		
	}
	
	private List<Object> resultSetToList(ResultSet rs, Class<?> clazz) {

		List<Object> res = new ArrayList<>();

		try {
			MetaModel<?> model = MetaConstructor.getInstance().getModels().get(clazz.getSimpleName());

			// Iterate over all results passed and add an object from the row
			while (rs.next()) {
				Object obj = model.getConstructor().newInstance();

				// Get Setters of the model
				Set<Map.Entry<Method, String[]>> setters = model.getSetters().entrySet();

				// Use each setter to set the field using ResultSet
				for (Map.Entry<Method, String[]> s : setters) {
					setField(obj, s.getKey(), rs, s.getValue()[0]);
				}

				// Add object to result array
				res.add(obj);
			}

		} catch (Exception e) {
			log.error("Error in converting ResultSet to List", e);
		}

		return res;
	}

	// Takes in the object it is acting on, the setter being invoked, the rs queried
	// and the column name
	private void setField(Object obj, Method setMethod, ResultSet rs, String column) {
		try {
			setMethod.invoke(obj, rs.getObject(column));
		} catch (Exception e) {
			log.error("Error in setting fields when retrieving", e);
		}
	}

}
