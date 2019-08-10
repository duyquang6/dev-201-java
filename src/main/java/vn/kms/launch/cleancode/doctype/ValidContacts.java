package vn.kms.launch.cleancode.doctype;

import vn.kms.launch.cleancode.annotation.Document;
import vn.kms.launch.cleancode.annotation.Header;
import vn.kms.launch.cleancode.model.Contact;

import java.io.IOException;
import java.io.Writer;

@Document(reportName = "valid-contacts")
public class ValidContacts extends vn.kms.launch.cleancode.doctype.Document {
    private static ValidContacts instance;

    @Header(value = "id")
    private String id;
    @Header(value = "first_name")
    private String firstName;
    @Header(value = "last_name")
    private String lastName;
    @Header(value = "day_of_birth")
    private String dayOfBirth;
    @Header(value = "address")
    private String address;
    @Header(value = "city")
    private String city;
    @Header(value = "state")
    private String state;
    @Header(value = "zip_code")
    private String zipCode;
    @Header(value = "mobile_phone")
    private String mobilePhone;
    @Header(value = "email")
    private String email;

    private ValidContacts() {
    }

    public static ValidContacts getInstance() {
        if (instance == null)
            instance = new ValidContacts();
        return instance;
    }

    public void writeDoc(Writer writer) throws IOException {
        for (Contact contact : getAnalysisResult().getAllContacts()) {
            if (!getAnalysisResult().getInvalidContacts().containsKey(contact.getId())) {
                writer.write(contact.toString() + "\r\n");
            }
        }
    }
}