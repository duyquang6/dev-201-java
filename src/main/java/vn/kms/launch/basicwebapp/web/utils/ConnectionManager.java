//
// Copyright (c) 2015 KMS Technology.
//
package vn.kms.launch.basicwebapp.web.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//TODO This class should use Connection Pool instead
public class ConnectionManager {
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionManager.class.getCanonicalName());
    private static ConnectionManager instance = new ConnectionManager();
    
    private String jdbc = "jdbc:mysql://localhost:3306/launch";
    private String driverClass = "com.mysql.jdbc.Driver";
    private String username = "root";
    private String password = "123456789";

    private ConnectionManager() {
    }

    /**
     * This method should only be called ONCE when starting up the application
     */
    public synchronized void init(String jdbc, String driverClass, String username, String password) {
        LOG.debug("Init attribute for Connection Manager");
        this.jdbc = jdbc;
        this.driverClass = driverClass;
        this.username = username;
        this.password = password;
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        // Check necessary driver
        Class.forName(driverClass);

        Properties userInfo = new Properties();
        userInfo.put("username", username);
        userInfo.put("password", password);

        return DriverManager.getConnection(jdbc, userInfo);
    }

    public static void close(AutoCloseable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (Exception ignore) {
        }
    }

    public static ConnectionManager getInstance() {
        return instance;
    }
}
