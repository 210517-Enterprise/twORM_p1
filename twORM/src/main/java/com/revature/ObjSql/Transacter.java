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
		} catch (SQLException sqle) {
			log.error(sqle);
			sqle.printStackTrace();
		}
	}

	public void disableAutoCommit(final Connection conn) {
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			log.warn("Failed to disable autocommit");
			e.printStackTrace();
		}
	}

	public void Commit(final Connection conn) {
		try {
			conn.commit();
		} catch (SQLException sqle) {
			log.error(sqle);
			sqle.printStackTrace();
		}
	}

	public void Rollback(final Connection conn) {
		try {
			conn.rollback();
		} catch (SQLException sqle) {
			log.error(sqle);
			sqle.printStackTrace();
		}
	}

	public void Rollback(final String name, final Connection conn) {
		try {
			if (savepoints.containsKey(name)) {
				conn.rollback(savepoints.get(name));
			} else {
				log.warn("Trying to access a non-existent savepoint");
			}
		} catch (SQLException sqle) {
			log.error(sqle);
			sqle.printStackTrace();
		}
	}

	public void Savepoint(final String name, final Connection conn) {
		try {
			final Savepoint save = conn.setSavepoint(name);
			savepoints.put(name, save);
		} catch (SQLException sqle) {
			log.error(sqle);
			sqle.printStackTrace();
		}
	}

	public void ReleaseSavepoint(final String name, final Connection conn) {
		try {
			if (savepoints.containsKey(name)) {
				conn.releaseSavepoint(savepoints.get(name));
			} else {
				log.warn("Trying to access a non-existent savepoint");
			}
		} catch (SQLException sqle) {
			log.error(sqle);
			sqle.printStackTrace();
		}
	}

	public void setTransaction(final Connection conn) {
		try {
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		} catch (SQLException sqle) {
			log.error(sqle);
			sqle.printStackTrace();
		}
	}

}
