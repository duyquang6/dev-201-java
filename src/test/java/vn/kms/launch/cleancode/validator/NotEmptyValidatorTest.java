package vn.kms.launch.cleancode.validator;

import org.junit.Before;
import org.junit.Test;
import vn.kms.launch.cleancode.model.Person;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class NotEmptyValidatorTest {
    private Person goodPerson;
    private Person badPerson;
    private NotEmptyValidator validator;
    private Field firstNameField;
    private Field lastNameField;

    @Before
    public void setUp() throws Exception {
        goodPerson = new Person();
        badPerson = new Person();
        firstNameField = Person.class.getDeclaredField("firstName");
        firstNameField.setAccessible(true);
        lastNameField = Person.class.getDeclaredField("lastName");
        lastNameField.setAccessible(true);
        validator = new NotEmptyValidator(badPerson);
        badPerson.setFirstName("");
        goodPerson.setLastName("Smaller");
    }

    @Test
    public void isValid() throws IllegalAccessException {
        assertFalse(validator.isValid(firstNameField));
        validator = new NotEmptyValidator(goodPerson);
        assertTrue(validator.isValid(lastNameField));
    }
}