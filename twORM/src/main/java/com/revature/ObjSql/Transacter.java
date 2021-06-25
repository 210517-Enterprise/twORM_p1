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

	public void enableAutoCommit(final Connection conn) {
		try {
			conn.setAutoCommit(true);
			log.info("Auto-commit enabled. DANGER MODE");
		} catch (SQLException e) {
			log.error("Unable to enable autocommit: " , e);
		}
	}

	public void disableAutoCommit(final Connection conn) {
		try {
			conn.setAutoCommit(false);
			log.info("Auto-commit disabled. SAFE MODE");
		} catch (SQLException e) {
			log.error("Failed to disable autocommit");
			e.printStackTrace();
		}
	}

	public void Commit(final Connection conn) {
		try {
			conn.commit();
			log.info("Transaction committed.");
		} catch (SQLException e) {
			log.error("Failure in committing transactions: ", e);
		}
	}

	public void Rollback(final Connection conn) {
		try {
			conn.rollback();
			log.info("Transaction not committed. Changes rolled back.");
		} catch (SQLException e) {
			log.error("Failed to roll back changes: ", e);
		}
	}

	public void Rollback(final String name, final Connection conn) {
		try {
			if (savepoints.containsKey(name)) {
				conn.rollback(savepoints.get(name));
				log.info("Transaction rolled back to save point: " + name);
			} else {
				log.warn("Trying to access a non-existent savepoint");
			}
		} catch (SQLException e) {
			log.error("Error in rolling back transaction: ",e);
		}
	}

	public void Savepoint(final String name, final Connection conn) {
		try {
			final Savepoint save = conn.setSavepoint(name);
			savepoints.put(name, save);
			log.info("Save point " + name + " set.");
		} catch (SQLException e) {
			log.error("Error in setting savepoint ", e);
		}
	}

	public void ReleaseSavepoint(final String name, final Connection conn) {
		try {
			if (savepoints.containsKey(name)) {
				conn.releaseSavepoint(savepoints.get(name));
				log.info("Save point " + name + " removed.");
			} else {
				log.warn("Trying to remove a non-existent savepoint");
			}
		} catch (SQLException e) {
			log.error("Failed to remove save point: ", e);
		}
	}

	public void setTransaction(final Connection conn) {
		try {
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			log.info("Transaction initiated");
		} catch (SQLException e) {
			log.error("Failed to begin transaction: ", e);
		}
	}

}
