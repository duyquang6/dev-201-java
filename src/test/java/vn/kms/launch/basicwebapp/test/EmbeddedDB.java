//
// Copyright (c) 2015 KMS Technology.
//
package vn.kms.launch.basicwebapp.test;

import java.io.File;

import org.flywaydb.core.Flyway;
import org.junit.BeforeClass;

import vn.kms.launch.basicwebapp.web.utils.ConnectionManager;

public class EmbeddedDB {
    /**
     * Initialize DB schema for unit-test
     */
    @BeforeClass
    public static void initEmbeddedDB() throws Exception {
        // TODO The following code should be called ONCE. Currently, it is
        // called for each class test run by unit-test
        String jdbc = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false;MODE=PostgreSQL;IGNORECASE=TRUE";
        ConnectionManager conManager = ConnectionManager.getInstance();
        conManager.init(jdbc, "org.h2.Driver", "", "");

        Flyway flyway = new Flyway();
        flyway.setDataSource(jdbc, "", "");
        flyway.setLocations("filesystem:/" + new File(".").getCanonicalPath() + "/src/main/resources/db/scripts/");
        flyway.migrate();
    }

}
