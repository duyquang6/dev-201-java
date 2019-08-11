package vn.kms.launch.cleancode.validator;

import org.junit.Before;
import org.junit.Test;
import vn.kms.launch.cleancode.model.Person;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;

public class MaxLengthValidatorTest {
    private Person goodPerson;
    private Person badPerson;
    private MaxLengthValidator validator;
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
        validator = new MaxLengthValidator(badPerson);
        badPerson.setFirstName("My name is longer than 10");
        goodPerson.setLastName("Smaller");
    }

    @Test
    public void isValid() throws IllegalAccessException {
        assertEquals(false, validator.isValid(firstNameField));
        validator = new MaxLengthValidator(goodPerson);
        assertEquals(true, validator.isValid(lastNameField));
    }
}