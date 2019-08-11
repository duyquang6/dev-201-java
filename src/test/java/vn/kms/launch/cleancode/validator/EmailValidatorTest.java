package vn.kms.launch.cleancode.validator;

import org.junit.Before;
import org.junit.Test;
import vn.kms.launch.cleancode.model.Contact;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;

public class EmailValidatorTest {
    private Contact goodContact;
    private Contact badContact;
    private EmailValidator validator;
    private Field emailField;

    @Before
    public void setUp() throws Exception {
        badContact = new Contact();
        goodContact = new Contact();
        goodContact.setEmail("nguyenduyquang06@gmail.com");
        badContact.setEmail("nguyenduyquang06gmail.com");
        emailField = Contact.class.getDeclaredField("email");
        emailField.setAccessible(true);
        validator = new EmailValidator(goodContact);
    }

    @Test
    public void isValid() throws NoSuchFieldException, IllegalAccessException {
        assertEquals(true, validator.isValid(emailField));

        validator = new EmailValidator(badContact);
        assertEquals(false, validator.isValid(emailField));
    }

    @Test
    public void getErrorMsg() {
        assertEquals("'nguyenduyquang06@gmail.com' is invalid email format", validator.getErrorMsg("nguyenduyquang06@gmail.com"));
    }
}