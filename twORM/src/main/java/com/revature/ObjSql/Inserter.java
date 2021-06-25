package com.revature.ObjSql;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.revature.Meta.MetaConstructor;
import com.revature.Meta.MetaModel;

public class Inserter {

	public static final Inserter ObjIns = new Inserter();

	private static Logger log = Logger.getLogger(Inserter.class);

	private Inserter() {
		super();
	}

	public static Inserter getInstance() {
		return ObjIns;
	}

	private String getColumns(final Collection<String> getters, String pkName) {
		return String.join(",", getters.stream().filter(s -> (!s.equals(pkName))).toArray(String[]::new));
	}
	
	private String getColumns(final Collection<String> getters) {
		return String.join(",", getters.stream().toArray(String[]::new));
	}

	private void setSerialID(final Object obj, final Optional<Map.Entry<Method, String[]>> setter,
			final PreparedStatement pstmt) {

		ResultSet rs;

		try {
			rs = pstmt.getGeneratedKeys();
			while (rs.next() && setter.isPresent()) {
				setter.get().getKey().invoke(obj, rs.getInt(setter.get().getValue()[0]));
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SQLException e) {
			log.error("Encountered exception in setting serial ID: ", e);
		}

	}

	public boolean confirmTable(final Object obj, final Connection conn) {
		ArrayList<String> tables = new ArrayList<String>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT table_name" + " FROM information_schema.TABLES"
					+ " WHERE table_schema = '" + conn.getSchema() + "'");

			while (rs.next()) {
				tables.add(rs.getString("table_name"));
			}
		} catch (SQLException e) {
			log.error("Fatal error in confirming table: ", e);
		}

		if (!tables.contains(obj.getClass().getSimpleName().toLowerCase())) {
			log.info("Entity is not in the database. Creating table for entity " + obj.getClass().getSimpleName());
			return makeEntity(obj, conn);
		}
		return true;
	}

	public boolean saveObject(final Object obj, final Connection conn) {
		log.info("Attempting to insert " + obj +". Confirming the table exists.");
		try {
			if (confirmTable(obj, conn)) {
				try {
					final MetaModel<?> model = MetaConstructor.getInstance().getModel(obj);
					final HashMap<String, Method> getters = model.getGetters();
					final String pk_name = model.getPrimary_key_name();
					final Optional<Map.Entry<Method, String[]>> setter = getSerialKeyEntry(pk_name, model.getSetters());
					final String args = model.getPkIsSerial() ? getArgs(getters.keySet().size() - 2) : getArgs(getters.keySet().size() - 1);
					final String columns = model.getPkIsSerial() ? getColumns(getters.keySet(), pk_name) : getColumns(getters.keySet());
					final String sql = "INSERT INTO " + model.getEntity() + " ( " + columns + " ) VALUES( " + args
							+ " )";
					final PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

					int index = 1;

					for (Map.Entry<String, Method> getter : getters.entrySet()) {
						if (!getter.getKey().equalsIgnoreCase(pk_name) || !model.getPkIsSerial()) {
							pstmt.setObject(index, getter.getValue().invoke(obj));
							index++;
						}
					}
					pstmt.executeUpdate();
					if (model.getPkIsSerial()) {
						setSerialID(obj, setter, pstmt);
					}

					// also place object inside of cache
					Cacher.getInstance().putObjInCache(obj);
					log.info("Successfully inserted " + obj + " into the database.");
					return true;
				} catch (Exception e) {
					log.error("Unexpected critical error in adding record: ",e);
				}
			}
		} catch (SecurityException e) {
			log.error("Unexpected Security Exception in adding record: ",e);
		}
		log.warn("Failed to insert " + obj + "into the databse.");
		return false;
	}

	public boolean makeEntity(final Object obj, final Connection conn) {

		final MetaModel<?> model = MetaConstructor.getInstance().getModel(obj);
		final HashMap<String, Method> getters = model.getGetters();
		final String columns[] = getColumns(getters.keySet(), model.getPrimary_key_name()).split(",");

		String sql = "CREATE TABLE " + model.getEntity() + " (";

		try {
			if (model.getPkIsSerial()) {
				sql += model.getPrimary_key_name() + " SERIAL PRIMARY KEY, ";
			} else {
				sql += model.getPrimary_key_name() + " "
						+ typeJavaToSql(obj.getClass().getDeclaredField(model.getPrimary_key_name()).getType())
						+ " PRIMARY KEY, ";
			}

			for (int i = 0; i < columns.length; i++) {
				if (i < columns.length - 1) {
					sql += columns[i] + " " + typeJavaToSql(obj.getClass().getDeclaredField(columns[i]).getType())
							+ ", ";
				} else {
					sql += columns[i] + " " + typeJavaToSql(obj.getClass().getDeclaredField(columns[i]).getType())
							+ ");";
				}
			}
		} catch (NoSuchFieldException e) {
			log.warn("Error in parsing columns", e);
		}

		try {
			final PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.execute();
			log.info("Created table " + model.getEntity() + " in the database.");
			return true;
		} catch (SQLException e) {
			log.warn("Error in makeEntity", e);
			return false;
		}
	}

	public String typeJavaToSql(Class<?> type) {

		if (type.equals(byte.class) || type.equals(Byte.class) || type.equals(int.class) || type.equals(Integer.class)
				|| type.equals(short.class) || type.equals(Short.class)) {
			return "INTEGER";
		} else if (type.equals(long.class) || type.equals(Long.class) || type.equals(BigInteger.class)) {
			return "BIGINT";
		} else if (type.equals(boolean.class) || type.equals(Boolean.class)) {
			return "BOOLEAN";
		} else if (type.equals(char.class) || type.equals(Character.class) || type.equals(String.class)) {
			return "VARCHAR(50)";
		} else if (type.equals(double.class) || type.equals(Double.class) || type.equals(float.class)
				|| type.equals(Float.class) || type.equals(BigDecimal.class)) {
			return "NUMERIC";
		} else {
			return "TEXT";
		}
	}

	protected String getArgs(final int length) {
		if (length <= 0) {
			return " ?";
		}
		return String.join(",", Collections.nCopies(length, "?")) + ",? ";
	}

	protected Optional<Map.Entry<Method, String[]>> getSerialKeyEntry(final String name,
			final HashMap<Method, String[]> setters) {

		return setters.entrySet().stream().filter(e -> e.getValue()[0].equals(name)).findFirst();

	}
};