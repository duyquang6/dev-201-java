package vn.kms.launch.cleancode.validator;

import org.junit.Before;
import org.junit.Test;
import vn.kms.launch.cleancode.model.Address;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;

public class StateCodeValidatorTest {
    private Address goodAddress;
    private Address badAddress;
    private StateCodeValidator validator;
    private Field stateField;

    @Before
    public void setUp() throws Exception {
        goodAddress = new Address();
        badAddress = new Address();
        stateField = Address.class.getDeclaredField("state");
        stateField.setAccessible(true);
        validator = new StateCodeValidator(badAddress);
        badAddress.setState("SA");
        goodAddress.setState("MA");
    }

    @Test
    public void isValid() throws IllegalAccessException {
        assertEquals(false, validator.isValid(stateField));
        validator = new StateCodeValidator(goodAddress);
        assertEquals(true, validator.isValid(stateField));
    }
}