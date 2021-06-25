
package com.revature.ObjSql;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.log4j.Logger;

import com.revature.Meta.MetaConstructor;
import com.revature.Meta.MetaModel;

public class Retriever {

	private static final Retriever ObjRet = new Retriever();

	private static Logger log = Logger.getLogger(Retriever.class);

	private Retriever() {
		super();
	}

	public static Retriever getInstance() {
		return ObjRet;
	}

	public Optional<List<Object>> getAllEntity(Class<?> clazz, Connection c) {

		final MetaModel<?> model = MetaConstructor.getInstance().getModel(clazz);
		String sql = "SELECT * FROM " + model.getEntity();

		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			List<Object> res = resultSetToList(rs, clazz);

			if (!res.isEmpty()) {
				log.info("Returning list of records that match " + clazz);
				return Optional.of(res);
			}

		} catch (SQLException e) {
			log.error("Failed to get all entities for " + clazz.getSimpleName() +" ", e);
		}

		log.warn("No entities found that match " + clazz);
		return Optional.empty();
	}

	public Optional<Object> retrieveObjectByPK(Class<?> clazz, Object primaryKey, Connection c) {
		try {
			final MetaModel<?> model = MetaConstructor.getInstance().getModel(clazz);
			final HashMap<String, Method> getters = model.getGetters();

			String[] pkColumn = { model.getPrimary_key_name() };
			String[] pk = { primaryKey.toString() };
			Optional<List<Object>> obj = Cacher.getInstance().getObjFromCache(clazz, getters, pkColumn, pk);
			if (obj.isPresent()) {
				log.info("Returning object from cache");
				return Optional.of(obj.get().get(0));
			} else {
				String sql = "SELECT * FROM " + model.getEntity() + " WHERE ";

				sql += model.getPrimary_key_name() + " = ?;";

				PreparedStatement stmt = c.prepareStatement(sql);

				stmt.setObject(1, primaryKey);

				ResultSet rs = stmt.executeQuery();

				List<Object> res = resultSetToList(rs, model.getClazz());

				if (!res.isEmpty()) {
					log.info("Returning object with primary key of " + primaryKey);
					return Optional.of(res.get(0));
				}
			}

		} catch (Exception e) {
			log.error("Error in retrieving by PK", e);
		}

		log.warn("No object found with primary key of " + primaryKey);
		return Optional.empty();
	}

	public Optional<List<Object>> retrieveByColumn(Class<?> clazz, String column, Object value, Connection c) {
		try {
			MetaModel<?> model = MetaConstructor.getInstance().getModel(clazz);
			String sql = "SELECT * FROM " + model.getEntity() + " WHERE ";

			sql += column + " = ?;";

			PreparedStatement stmt = c.prepareStatement(sql);

			stmt.setObject(1, value);

			ResultSet rs = stmt.executeQuery();

			List<Object> res = resultSetToList(rs, model.getClazz());

			if (!res.isEmpty()) {
				log.info("Returning objects where column " + column + " equals " + value);
				return Optional.of(res);
			}

		} catch (Exception e) {
			log.error("Error in retrieving by column", e);

		}

		log.info("No object exists where column " + column + "is equal to" + value);
		return Optional.empty();
	}

	public Optional<List<Object>> retrieveByColumns(Class<?> clazz, HashMap<String, Object> columns, Connection c) {

		try {
			MetaModel<?> model = MetaConstructor.getInstance().getModel(clazz);
			String sql = "SELECT * FROM " + model.getEntity() + " WHERE ";
			List<Object> values = new ArrayList<>();
			for (String column : columns.keySet()) {
				sql += column + " = ? AND ";
				values.add(columns.get(column));
			}

			PreparedStatement stmt = c.prepareStatement(sql.substring(0, sql.length() - 5));

			for (int i = 1; i <= values.size(); i++) {
				stmt.setObject(i, values.get(i - 1));
			}

			ResultSet rs = stmt.executeQuery();

			List<Object> res = resultSetToList(rs, model.getClazz());

			if (!res.isEmpty()) {
				log.info("Return results found by values in multiple columns.");
				return Optional.of(res);
			}

		} catch (Exception e) {
			log.error("Error in retrieving by multiple columns", e);

		}
		
		log.warn("No object was found where the indicated columns have the indicated values");
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
			MetaModel<?> model = MetaConstructor.getInstance().getModel(clazz);

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
			Object o = rs.getObject(column);
			System.out.println(o.getClass());
			setMethod.invoke(obj, o);
		} catch (Exception e) {
			log.error("Error in setting fields when retrieving", e);
		}
	}

}