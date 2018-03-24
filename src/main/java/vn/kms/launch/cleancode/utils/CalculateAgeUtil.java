package vn.kms.launch.cleancode.utils;

import vn.kms.launch.factory.AgeGroup;
import vn.kms.launch.factory.AgeGroupFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CalculateAgeUtil {

    public static String calculateAgeGroup(int age){
        if (age <= 9) {
            return "Children";
        } else if (age < 19) {
            return "Adolescent";
        } else if (age <= 45) {
            return "Adult";
        } else if (age <= 60) {
            return "Middle Age";
        } else {
            return "Senior";
        }
    }

    // TODO: Calculate age exactly by month/day/year -> done.
    public static int calculateExactlyAge(String dateOfBirth) {
        try {
            LocalDate birthday = LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            LocalDate now = LocalDate.now();
            return now.getYear() - birthday.getYear();
        } catch (DateTimeParseException ex) {
            return -1;//Date time parser error
        }
    }
}
