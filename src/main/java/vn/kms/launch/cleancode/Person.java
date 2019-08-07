package vn.kms.launch.cleancode;

public class Person {
    @Column(header = "first_name")
    private String firstName;

    @Column(header = "last_name")
    private String lastName;

    @Column(header = "date_of_birth")
    private String dayOfBirth;

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

    public String getDayOfBirth() {
        return dayOfBirth;
    }

    @Column(header = "date_of_birth")
    public void setDayOfBirth(String dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String toString() {
        return String.format("%s\t%s\t%s", firstName, lastName, dayOfBirth);
    }
}
