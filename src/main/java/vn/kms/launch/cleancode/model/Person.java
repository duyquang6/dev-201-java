package vn.kms.launch.cleancode.model;

import vn.kms.launch.cleancode.annotation.Column;
import vn.kms.launch.cleancode.annotation.Header;
import vn.kms.launch.cleancode.annotation.validator.LengthEqual;
import vn.kms.launch.cleancode.annotation.validator.MaxLength;
import vn.kms.launch.cleancode.annotation.validator.NotEmpty;
import vn.kms.launch.cleancode.annotation.validator.NotNull;

import static vn.kms.launch.cleancode.utils.AgeCalculation.preciseCalculateAge;

public class Person {
    @Header(value = "first_name")
    @NotEmpty
    @MaxLength(length = 10)
    private String firstName;
    @Header(value = "last_name")
    @NotEmpty
    @MaxLength(length = 10)
    private String lastName;
    @Header(value = "date_of_birth")
    @LengthEqual(length = 10)
    @NotNull
    private String dateOfBirth;
    private int age;

    public String getFirstName() {
        return firstName;
    }

    @Column(header = "first_name")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Column(header = "last_name")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    @Column(header = "date_of_birth")
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getAge() {
        return age;
    }

    public void setAge() {
        this.age = preciseCalculateAge(dateOfBirth);
    }

    public String toString() {
        return String.format("%s\t%s\t%s", firstName, lastName, dateOfBirth);
    }
}
