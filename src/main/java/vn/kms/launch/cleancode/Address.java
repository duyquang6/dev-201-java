package vn.kms.launch.cleancode;

public class Address {
    private String houseNumberAndStreet;
    private String city;
    private String state;
    private String zipCode;

    public String getHouseNumberAndStreet() {
        return houseNumberAndStreet;
    }

    public void setHouseNumberAndStreet(String houseNumberAndStreet) {
        this.houseNumberAndStreet = houseNumberAndStreet;
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

    public String toString() {
        return String.format("%s\t%s\t%s\t%s", houseNumberAndStreet,
                city, state, zipCode);
    }
}
