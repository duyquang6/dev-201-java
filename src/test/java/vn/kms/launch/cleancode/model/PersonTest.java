package vn.kms.launch.cleancode.model;

import junit.framework.TestCase;
import org.junit.Before;

public class PersonTest extends TestCase {
    private Person person;

    @Before
    public void setUp() throws Exception {
        person = new Person();
        person.setFirstName("Quang");
        person.setLastName("Nguyen Duy");
        person.setDateOfBirth("07/25/1996");
        person.setAge();
    }

    public void testGetAge() {
        person.setDateOfBirth("07/25/1996");
        person.setAge();
        assertEquals(23, person.getAge());
        person.setDateOfBirth("09/25/1996");
        person.setAge();
        assertEquals(23, person.getAge());
    }

    public void testToString() {
        assertEquals("Quang\tNguyen Duy\t07/25/1996", person.toString());
    }
}