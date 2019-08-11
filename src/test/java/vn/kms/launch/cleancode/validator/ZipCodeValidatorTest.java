package vn.kms.launch.cleancode.validator;

import org.junit.Before;
import org.junit.Test;
import vn.kms.launch.cleancode.model.Address;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;

public class ZipCodeValidatorTest {
    private Address goodAddress;
    private Address badAddress;
    private ZipCodeValidator validator;
    private Field zipCodeField;

    @Before
    public void setUp() throws Exception {
        goodAddress = new Address();
        badAddress = new Address();
        zipCodeField = Address.class.getDeclaredField("zipCode");
        zipCodeField.setAccessible(true);
        validator = new ZipCodeValidator(badAddress);
        badAddress.setZipCode("455");
        goodAddress.setZipCode("70000");
    }

    @Test
    public void isValid() throws IllegalAccessException {
        assertEquals(false, validator.isValid(zipCodeField));
        validator = new ZipCodeValidator(goodAddress);
        assertEquals(true, validator.isValid(zipCodeField));
    }
}