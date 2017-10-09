//
// Copyright (c) 2015 Health Market Science, Inc.
//
package vn.kms.launch.basicwebapp.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestClass {
    private static final Logger LOG = LoggerFactory.getLogger(TestClass.class.getCanonicalName());

    @BeforeClass
    public static void classInit() {
        LOG.debug("Init Test Class: This will be printed once");
    }

    @AfterClass
    public static void classCleanup() {
        LOG.debug("Cleanup Test Class: This will be printed once");
    }

    @Before
    public void init() {
        LOG.debug("Init Test Method: This will be printed twice");
    }

    @After
    public void cleanup() {
        LOG.debug("Cleanup Test Method: This will be printed twice");
    }

    @Test
    public void test1() {
        LOG.debug("Running test case 1");
        Assert.assertEquals(true, true);
    }

    @Test
    public void test2() {
        LOG.debug("Running test case 2");
        Assert.assertEquals(false, false);
    }
}
