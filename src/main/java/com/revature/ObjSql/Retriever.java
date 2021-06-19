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

			return resultSetToList(rs, clazz);

		} catch (SQLException e) {
			log.error("Failed to get all entities for " + clazz.getSimpleName(), e);
		}

		return Optional.empty();
	}

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
			log.error("Error in finding object of Entity" + obj.getClass().getSimpleName(), e);
		}

		return Optional.empty();
	}

	public Optional<Object> retrieveObjectByPK(String entity, Object primaryKey, Connection c) {
		String sql = "SELECT * FROM " + entity + " WHERE ? = ?;";
		try {
			MetaModel<?> model = MetaConstructor.getInstance().getModels().get(entity);
			Optional<Object> obj = (Optional<Object>) model.getConstructor().newInstance();
			Set<Map.Entry<Method, String[]>> setters = model.getSetters().entrySet();
			
			PreparedStatement stmt = c.prepareStatement(sql);
			
			stmt.setString(1, model.getPrimary_key_name());
			stmt.setObject(2, primaryKey);
			
			ResultSet rs = stmt.executeQuery();
			
			for (Map.Entry<Method, String[]> s : setters) {
				setField(obj, s.getKey(), rs, s.getValue()[0]);
			}
			
			return obj;
			
		} catch (Exception e) {
			log.error(e);
			return Optional.empty();
		}
    }

	private Optional<List<Object>> resultSetToList(ResultSet rs, Class<?> clazz) {

		try {
			List<Object> res = new ArrayList<>();
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

			if (res.isEmpty()) {
				return Optional.empty();
			}

			return Optional.of(res);

		} catch (Exception e) {
			log.error("Error in converting ResultSet to List", e);
		}

		return Optional.empty();
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
