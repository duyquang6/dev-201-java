package vn.kms.launch.cleancode.model;

import vn.kms.launch.cleancode.annotation.Column;
import vn.kms.launch.cleancode.annotation.Header;
import vn.kms.launch.cleancode.annotation.validator.MaxLength;
import vn.kms.launch.cleancode.annotation.validator.StateCodeValid;
import vn.kms.launch.cleancode.annotation.validator.ZipCodeValid;

public class Address {
    @Header(value = "address")
    @MaxLength(length = 20)
    private String houseNumberAndStreet;

    @Header(value = "city")
    @MaxLength(length = 15)
    private String city;

    @Header(value = "state")
    @StateCodeValid
    private String state;

    @Header(value = "zip")
    @ZipCodeValid
    private String zipCode;

    public String getHouseNumberAndStreet() {
        return houseNumberAndStreet;
    }

    @Column(header = "address")
    public void setHouseNumberAndStreet(String houseNumberAndStreet) {
        this.houseNumberAndStreet = houseNumberAndStreet;
    }

    public String getCity() {
        return city;
    }

    @Column(header = "city")
    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    @Column(header = "state")
    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    @Column(header = "zip")
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String toString() {
        return String.format("%s\t%s\t%s\t%s", houseNumberAndStreet,
                city, state, zipCode);
    }
}
