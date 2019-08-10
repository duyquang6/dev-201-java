package vn.kms.launch.cleancode.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AddressTest {
    private Address address;

    @Before
    public void setUp() throws Exception {
        address = new Address();
        address.setCity("Cambridge");
        address.setHouseNumberAndStreet("25 Se 176th Pl");
        address.setState("MA");
        address.setZipCode("2138");
    }

    @Test
    public void addressToString() {
        assertEquals("25 Se 176th Pl\tCambridge\tMA\t2138", address.toString());
    }
}