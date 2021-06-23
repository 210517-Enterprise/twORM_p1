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

/**
 * <p>Provides the primary user functions to implement the TwORM framework within a project. Contains all necessary commands for
 * transaction control and CRUD functions as well as accessing the cache. Use the TwORM.getInstance() to create
 * an instance.</p>
 * @author James Butler, Thomas Ceci, and Frederick Thornton
 * @version 0.2
 * @since 0.1
 *
 */

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
	
	/**
	 * <p>Returns an instance of the TwORM to be used.</p>
	 * @return TwORM
	 */
	public static TwORM getInstance() {
		return twORM;
	}
	
	/**
	 * <p>Returns the cache as an object that can be interacted with.</p>
	 * @return cache as a hashmap
	 */
	public HashMap<Class<?>, HashSet<Object>> getCache() {
		return Cacher.getInstance().getCache();
	}
	
	/**
	 * <p>Adds a class to the meta-constructor. This method must be called for
	 * every class that is used in the framework. Classes passed to the method should
	 * be annotated appropriately.
	 * @param clazz : a class annotated with the TwORM annotations
	 * @return boolean value for whether a a class was added to the constructor.
	 */
	public boolean addClass(final Class<?> clazz) {
		try {
			constructor.addModel(clazz);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * <p>Takes an object in and will update the values stored for that object in the database and cache.</p>
	 * @param obj: the object that needs to be updated
	 * @return boolean value of success or failure
	 */
	public boolean updateObjectInDB(final Object obj) {
		try {
			return updater.updateObject(obj, conn);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	/**
	 * <p>Removes the designated object from the database.</p>
	 * @param obj: the object to be removed
	 * @return boolean value of success
	 */
	public boolean deleteObjectFromDB(final Object obj) {
		return deleter.removeObjectFromDB(obj, conn);
	}
	
	/**
	 * Checks the database for whether an appropriate table exists. If no table exists, one will be created and then
	 * the object will be inserted into the database. If an appropriate table exists, the object is inserted directly.
	 * @param obj: object to be inserted into the database
	 * @return boolean value of success
	 */
	public boolean addObjectToDb(final Object obj) {
		return inserter.saveObject(obj, conn);
	}
	
	
	/**
	 * Queries and returns a list of all entries in a table in the database. 
	 * @param clazz: the class which represents the table to be queried
	 * @return an Optional<List> of objects. Each object represents a single entry
	 * in the database
	 */
	public Optional<List<Object>> getListObjectFromDB(final Class<?> clazz) {
        return rover.getAllEntity(clazz,conn);
        }
	
	/**
	 * Queries the DB and returns a single object based on the provided primary key.
	 * @param clazz: the class that represents the table to be searched
	 * @param pk: the primary key an an Optional<object> which permits passing
	 * any data type.
	 * @return an object of Class clazz with the information from the database.
	 */
	public Optional<Object> getByPK(Class<?> clazz, String pk) {
		return rover.retrieveObjectByPK(clazz, pk, conn);
	}
	
	public Optional<Object> getByPK(Class<?> clazz, int pk) {
		return rover.retrieveObjectByPK(clazz, pk, conn);
	}
	
	/**
	 * <p>Returns a list of objects selected by a value in a column that is not the primary key</p>
	 * @param clazz: the class that represents the table in the DB
	 * @param column: the name of the column in the DB to be selected against (should match the @Column annotation!)
	 * @param value: the value to be searched
	 * @return an Optional<List> of objects. Each object in the list represents a single entry in the DB.
	 */
	public Optional<List<Object>> getListByColumn(Class<?> clazz, String column, Object value) {
		return rover.retrieveByColumn(clazz, column, value, conn);
	}

	/**
	 * A transaction control statement that will commit all database changes if autoCommit is set to false.
	 */
	public void commitChanges() {
		transacter.Commit(conn);
	}
	
	/**
	 * A transaction control statement that will roll back all database changes while autoCommit is set to false.
	 */
	public void abortChanges() {
		transacter.Rollback(conn);
	}
	
	/**
	 * A transaction control statement that will roll back all database changes to the specified save point.
	 * @param savePointName: the name of the save point that should be rolled back to
	 */
	private void returnToSavePoint(String savePointName) {
		transacter.Rollback(savePointName, conn);
	}
	
	/**
	 * A transaction control statement that remove a save point so that it is no longer available.
	 * @param savePointName: the save point to be removed
	 */
	public void removeSavePoint(String savePointName) {
		transacter.ReleaseSavepoint(savePointName, conn);
	}
	
	/**
	 * A transaction control statement that will turn ON autocommit. 
	 */
	public void enableAutoCommit() {
		transacter.enableAutoCommit(conn);
	}
	
	/**
	 * A transaction control statement that will turn OFF autocommit. This is the default value.
	 */
	public void disableAutoCommit() {
	 		transacter.disableAutoCommit(conn);
	}
	
	/**
	 * A transaction control statement that will set transaction isolation to TRANSACTION_READ_COMMITTED
	 */
	public void setTransactionLevel() {
		transacter.setTransaction(conn);
	}
	
	/*not yet implemented in retriever
	public void addAllFromDBToCache(final Class<?> clazz) {
	       rover.queryAllFromDB(clazz,conn);
	    }
	*/
}
