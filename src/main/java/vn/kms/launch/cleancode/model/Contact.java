package vn.kms.launch.cleancode.model;

import vn.kms.launch.cleancode.annotation.Column;
import vn.kms.launch.cleancode.annotation.Header;
import vn.kms.launch.cleancode.annotation.validator.EmailValid;
import vn.kms.launch.cleancode.annotation.validator.PhoneNumberValid;
import vn.kms.launch.cleancode.validator.FieldValidation;

import java.util.Map;

import static vn.kms.launch.cleancode.utils.Other.mapDataToObject;
import static vn.kms.launch.cleancode.utils.Other.tryGetIntWithDefault;

public class Contact implements Comparable<Contact> {
    @Header(value = "id")
    private int id;
    private Person person;
    private Address address;
    @Header(value = "phone1")
    @PhoneNumberValid
    private String phoneNumber;
    @Header(value = "email")
    @EmailValid
    private String email;
    private FieldValidation fieldValidation;

    public Contact() {
        super();
        this.person = new Person();
        this.address = new Address();
    }

    public int getId() {
        return id;
    }

    @Column(header = "id", type = "INTEGER")
    public void setId(int id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public Address getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Column(header = "phone1")
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    @Column(header = "email")
    public void setEmail(String email) {
        this.email = email;
    }

    public String toString() {
        return String.format("%s\t%s\t%s\t%s\t%s", id, person.toString(), address.toString(), phoneNumber, email);
    }

    public void loadDataToEntity(Map<String, Integer> headerIndexByFieldName, String[] dataColumns) {
        mapDataToObject(dataColumns, headerIndexByFieldName, person);
        mapDataToObject(dataColumns, headerIndexByFieldName, address);
        mapDataToObject(dataColumns, headerIndexByFieldName, this);
    }

    public FieldValidation validate(Map<String, Integer> fieldErrorCounts) throws IllegalAccessException {
        this.fieldValidation = new FieldValidation();
        fieldValidation.validateObject(this.getPerson(), fieldErrorCounts);
        fieldValidation.validateObject(this.getAddress(), fieldErrorCounts);
        fieldValidation.validateObject(this, fieldErrorCounts);
        return fieldValidation;
    }

    public boolean isValid() throws IllegalAccessException {
        if (fieldValidation == null) {
            this.validate(null);
        }
        return fieldValidation.getErrors().isEmpty();
    }

    @Override
    public int compareTo(Contact that) {
        Integer thisZipCode = tryGetIntWithDefault(this.address.getZipCode(), 0);
        Integer thatZipCode = tryGetIntWithDefault(that.address.getZipCode(), 0);
        return thisZipCode.compareTo(thatZipCode);
    }
}