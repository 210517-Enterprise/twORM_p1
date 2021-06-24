package com.revature.connection;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

public class ConnectionFactory {

    private static final ConnectionFactory cf = new ConnectionFactory();
    private BasicDataSource ds;
	private Logger log = Logger.getLogger(ConnectionFactory.class);

    static {
        try {
            Class.forName("org.postgresql.Driver");
        }catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }
	
    private ConnectionFactory() {
        try {
            Properties props = new Properties();
            props.load(new FileReader("src/main/resources/application.properties"));
            ds = new BasicDataSource();
            ds.setUrl(props.getProperty("DB_URL"));
            ds.setUsername(props.getProperty("DB_USERNAME"));
            ds.setPassword(props.getProperty("DB_PASSWORD"));
            // Connection pooling
            ds.setMinIdle(5);
            ds.setDefaultAutoCommit(true);
            ds.setMaxIdle(10);
            ds.setMaxOpenPreparedStatements(100);
        }catch(IOException e) {
        	log.error("Could not find application.properties");
        }
    }
    
    public static ConnectionFactory getInstance() {
        return cf;
    }
    
    public Connection getConnection () {
        try {
            return ds.getConnection();
        }catch (SQLException e) {
        	log.warn("Error in getting connection" + e);
        }
        return null;
    }
}
