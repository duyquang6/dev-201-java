package vn.kms.launch.cleancode;

public class Util {
    private static int yearOfReport = 2016;
    public static int calculateAgeByYear(String dateOfBirth) {
        String yearStr = dateOfBirth.split("/")[2];
        int year = Integer.parseInt(yearStr);
        return yearOfReport - year;
    }

    // TODO: Calculate age exactly by month/day/year.
    // Chưa hiểu đề
    public static int preciseCalculateAge(String dateOfBirth) {
        return 10;
    }

    private static String convertAgeToGroup(int age) {
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


}
