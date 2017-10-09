//
// Copyright (c) 2015 KMS Technology.
//
package vn.kms.launch.basicwebapp.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vn.kms.launch.basicwebapp.web.utils.ConnectionManager;

public class ConnectionManagerTest {
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionManagerTest.class.getCanonicalName());

    @Test
    public void testConnection() {
        try {
            String jdbc = "jdbc:h2:mem:test;DB_CLOSE_ON_EXIT=false";
            ConnectionManager conManager = ConnectionManager.getInstance();
            conManager.init(jdbc, "org.h2.Driver", "sa", "");

            Connection connection = null;
            PreparedStatement statement = null;
            ResultSet result = null;
            try {
                connection = conManager.getConnection();
                statement = connection.prepareStatement("SELECT 1");
                result = statement.executeQuery();
                while (result.next()) {
                    LOG.info(result.getString(1));
                }
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            } finally {
                ConnectionManager.close(result);
                ConnectionManager.close(statement);
                ConnectionManager.close(connection);
            }

        } catch (ClassNotFoundException e) {
            LOG.error("Missing necessary Driver: " + e.getMessage());
        }
    }
}
