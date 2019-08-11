package vn.kms.launch.cleancode.validator;

import org.junit.Before;
import org.junit.Test;
import vn.kms.launch.cleancode.model.Person;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;

public class LengthEqualValidatorTest {
    private Person goodPerson;
    private Person badPerson;
    private LengthEqualValidator validator;
    private Field dobField;

    @Before
    public void setUp() throws Exception {
        goodPerson = new Person();
        badPerson = new Person();
        dobField = Person.class.getDeclaredField("dateOfBirth");
        dobField.setAccessible(true);
        validator = new LengthEqualValidator(goodPerson);
        goodPerson.setDateOfBirth("07/25/1996");
        badPerson.setDateOfBirth("0725-1996");
    }

    @Test
    public void isValid() throws IllegalAccessException {
        assertEquals(true, validator.isValid(dobField));

        validator = new LengthEqualValidator(badPerson);
        assertEquals(false, validator.isValid(dobField));
    }
}