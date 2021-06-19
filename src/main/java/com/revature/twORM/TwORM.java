package com.revature.twORM;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import com.revature.connection.ConnectionFactory;
import com.revature.Meta.MetaConstructor;
import com.revature.ObjSql.*;

public class TwORM {

	private static final TwORM twORM = new TwORM();
	private final MetaConstructor constructor;
	private final Inserter inserter;
	private final Retriever rover;
	private final Deleter deleter;
	private final Updater updater;
	private final Transacter transacter;
	private final Connection conn;
	
	private TwORM() {
		super();
		conn = ConnectionFactory.getInstance().getConnection();
		transacter = Transacter.getTransaction();
		constructor = MetaConstructor.getInstance();
		inserter = Inserter.getInstance();
		rover = Retriever.getInstance();
		deleter = Deleter.getInstance();
		updater = Updater.getInstance();
	}
	
	public static TwORM getInstance() {
		return twORM;
	}
	
	public HashMap<Class<?>, HashSet<Object>> getCache() {
		return Cacher.getInstance().getCache();
	}
	
	public boolean addClass(final Class<?> clazz) {
		try {
			constructor.addModel(clazz);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean updateObjectInDB(final Object obj) {
		try {
			return updater.updateObject(obj, conn);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/*This might be more elegant:
	 *This way would only update changed information instead of the entire object 
	
	public boolean UpdateObjectInDB(final Object obj,final String update_columns) {
        return rover.updateObject(obj,update_columns,conn);
    }
	*/
	
	public boolean deleteObjectFromDB(final Object obj) {
		return deleter.removeObjectFromDB(obj, conn);
	}
	
	public boolean addObjectToDb(final Object obj) {
		return inserter.saveObject(obj, conn);
	}
	
	/*
	 * Retriver is not implemented yet. Uncomment these when it is
	public Optional<List<Object>> getListObjectFromDB(final Class<?> clazz, final String columns, final String conditions) {
		return rover.getListObject(clazz, columns, conditions, "", conn);
	}
	
	
	
	
	public Optional<List<Object>> getListObjectFromDB(final Class<?> clazz, final String columns, final String conditions, final String operators) {
		return rover.getListObjectFromDB(clazz, columns, conditions, operators, conn);
	}
	
	
	public Optional<List<Object>> getListObjectFromDB(final Class<?> clazz) {
        return rover.queryAllFromDB(clazz,conn);
        }
        */
	
	public void commitChanges() {
		transacter.Commit(conn);
	}
	
	public void abortChanges() {
		transacter.Rollback(conn);
	}
	
	private void returnToSavePoint(String savePointName) {
		transacter.Rollback(savePointName, conn);
	}
	
	public void removeSavePoint(String savePointName) {
		transacter.ReleaseSavepoint(savePointName, conn);
	}
	
	public void enableAutoCommit() {
		transacter.enableAutoCommit(conn);
	}
	
	/* This doesn't exist yet, but perhaps it should
	 * public void disableAutoCommit() {
	 * 		transacter.disableAutoCommit(conn);
	 */
	
	public void beginTransaction() {
		transacter.setTransaction(conn);
	}
	
	/*not yet implemented in retriever
	public void addAllFromDBToCache(final Class<?> clazz) {
	       rover.queryAllFromDB(clazz,conn);
	    }
	*/
}
