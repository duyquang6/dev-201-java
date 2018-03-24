package vn.kms.launch.cleancode.entities;

import vn.kms.launch.cleancode.annotation.*;
import vn.kms.launch.cleancode.common.Column;
import vn.kms.launch.cleancode.common.CommonVariables;

public class Contact extends CommonVariables{

    @Column(value = "id")
    private int id;

    @NotEmpty
    @ValidLength
    @Column(value = "first_name")
    private String firstName;

    @NotEmpty
    @ValidLength
    @Column(value = "last_name")
    private String lastName;

    @NotNull
    @DobLength
    @Column(value = "date_of_birth")
    private String dateOfBirth;

    @ValidLength
    @Column(value = "address")
    private String address;

    @ValidLength
    @Column(value = "city")
    private String city;

    @ValidStateCodes
    @Column(value = "state")
    private String state;

    @Pattern(value = "^\\\\d{4,5}$")
    @Column(value = "zip_code")
    private String zipCode;

    @Pattern(value = "^\\\\d{3}\\\\-\\\\d{3}\\\\-\\\\d{4}$")
    @Column(value = "mobile_phone")
    private String mobilePhone;

    @Pattern(value = "^.+@.+\\\\..+$")
    @Column(value = "email")
    private String email;

    public Contact(){
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        int age = calculateAgeUtil.calculateExactlyAge(getDateOfBirth());
        return age;
    }

    @Override
    public String toString() {
        return "Contact [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", address=" + address
                    + ", city=" + city + ", state=" + state + ", zipCode=" + zipCode + ", mobilePhone=" + mobilePhone
                    + ", email=" + email + ", age=" + getAge() + ", dayOfBirth=" + dateOfBirth + "]";
    }

}
