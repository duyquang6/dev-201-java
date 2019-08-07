package vn.kms.launch.cleancode;

public class Address {
    @Column(header = "address")
    private String houseNumberAndStreet;

    @Column(header = "city")
    private String city;

    @Column(header = "state")
    private String state;

    @Column(header = "zip")
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
