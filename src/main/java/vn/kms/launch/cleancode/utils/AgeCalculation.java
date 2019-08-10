package vn.kms.launch.cleancode.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AgeCalculation {

    public static String convertAgeToGroup(int age) {
        if (age <= 9) {
            return "Children";
        } else if (age <= 19) {
            return "Adolescent";
        } else if (age <= 45) {
            return "Adult";
        } else if (age <= 60) {
            return "Middle Age";
        } else {
            return "Senior";
        }
    }

    public static int preciseCalculateAge(String dateOfBirth) {
        try {
            LocalDate dateDayOfBirth = LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            LocalDate momentNow = LocalDate.now();
            return momentNow.getYear() - dateDayOfBirth.getYear();
        } catch (DateTimeParseException ex) {
            return -1;
        }
    }

    public static int calculateAgeByYear(String dateOfBirth, int yearOfReport) {
        String yearStr = dateOfBirth.split("/")[2];
        int year = Integer.parseInt(yearStr);
        int age = yearOfReport - year;
        return age;
    }
}
