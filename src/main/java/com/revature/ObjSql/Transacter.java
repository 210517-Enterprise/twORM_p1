package com.revature.ObjSql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.HashMap;

import org.apache.log4j.Logger;

public class Transacter {

	private static Logger log = Logger.getLogger(Transacter.class);

	private static final Transacter trans = new Transacter();

	private final HashMap<String, Savepoint> savepoints;

	private Transacter() {
		super();
		savepoints = new HashMap<>();
	}

	public static Transacter getTransaction() {
		return trans;
	}

	/**
	 * turns on auto commit for this connection
	 * 
	 * @param conn connection to turn auto commit on for.
	 */
	public void enableAutoCommit(final Connection conn) {
		try {
			conn.setAutoCommit(true);
		} catch (SQLException sqle) {
			log.error(sqle);
		}
	}

	/**
	 * Commit all uncommitted SQL transactions for this connection.
	 * 
	 * @param conn connection to commit transactions on.
	 */
	public void Commit(final Connection conn) {
		try {
			conn.commit();
		} catch (SQLException sqle) {
			log.error(sqle);
		}
	}

	/**
	 * Rollback all uncommitted SQL transactions.
	 * 
	 * @param conn connection to rollback on.
	 */
	public void Rollback(final Connection conn) {
		try {
			conn.rollback();
		} catch (SQLException sqle) {
			log.error(sqle);
		}
	}

	/**
	 * Rollback to a previous savepoint.
	 * 
	 * @param name name of savepoint.
	 * @param conn connection to rollback on.
	 */
	public void Rollback(final String name, final Connection conn) {
		try {
			if (savepoints.containsKey(name)) {
				conn.rollback(savepoints.get(name));
			} else {
				log.warn("Trying to access a non-existent savepoint");
			}
		} catch (SQLException sqle) {
			log.error(sqle);
		}
	}

	/**
	 * Create a named savepoint.
	 * 
	 * @param name name of new savepoint
	 * @param conn connection for savepoint.
	 */
	public void Savepoint(final String name, final Connection conn) {
		try {
			final Savepoint save = conn.setSavepoint(name);
			savepoints.put(name, save);
		} catch (SQLException sqle) {
			log.error(sqle);
		}
	}

	/**
	 * Release a previously made savepoint.
	 * 
	 * @param name name of savepoint.
	 * @param conn connection.
	 */
	public void ReleaseSavepoint(final String name, final Connection conn) {
		try {
			if (savepoints.containsKey(name)) {
				conn.releaseSavepoint(savepoints.get(name));
			} else {
				log.warn("Trying to access a non-existent savepoint");
			}
		} catch (SQLException sqle) {
			log.error(sqle);
		}
	}

	/**
	 * Set a transaction in SQL.
	 * 
	 * @param conn connection.
	 */
	public void setTransaction(final Connection conn) {
		try {
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		} catch (SQLException sqle) {
			log.error(sqle);
		}
	}

}
