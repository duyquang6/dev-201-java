package vn.kms.launch.cleancode.utils;

import vn.kms.launch.cleancode.entities.Contact;
import vn.kms.launch.cleancode.common.CommonVariables;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;

public class HandleFileUtils extends CommonVariables {
    File outputFile;
    Writer reportWriter;
    Map<String, Integer> reportContents;

    public void loadDataFromFile() {
        try {
            InputStream inputData = new FileInputStream("data/contacts.tsv");
            int readOneCharacter;

            while ((readOneCharacter = inputData.read()) != -1) {
                guestFileSizeToUseBuffer[countTotalCharacterInFile] = (char) readOneCharacter;
                countTotalCharacterInFile++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void storeValidContactsReportToFile() {
        try {
            createOutputFolder();
            reportWriter = new FileWriter(new File(outputFile, "valid-contacts.tab"));
            reportWriter.write(VALID_CONTACTS_REPORT_HEADERS);

            for (Contact contact : allContacts) {
                if (!contactDetails.containsKey(contact.getId())) {
                    reportWriter.write(contact.toString() + "\r\n");

                    //display valid contacts stored into file on console
                    System.out.println(contact.toString());
                }
            }
            reportWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createOutputFolder() {
        outputFile = new File("output");
        if (!outputFile.exists()) {
            outputFile.mkdirs();//create output folder
        }
    }

    public void storeReportsToFile() {
        try {
            for (Object object : REPORT_HEADERS.keySet()) {
                String reportType = (String) object;
                createOutputFolder();

                reportWriter = new FileWriter(new File(outputFile, reportType + ".tab"));
                reportWriter.write(REPORT_HEADERS.get(reportType));

                reportContents = (Map<String, Integer>) reports.get(reportType);
                if (reportType.equals("contact-per-age-group")) {
                    storeContactPerAgeGroupReportToFile();
                } else if (reportType.equals("contact-per-state")) {
                    storeContactPerStateReportToFile();
                } else if (reportType.equals("invalid-contact-summary")) {
                    storeInvalidContactsSummaryReportToFile();
                } else { //invalid-contact-details
                    storeInvalidContactDetailsReportToFile();
                }
                reportWriter.flush();
                System.out.println("Generated report " + "output/" + reportType + ".tab");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void storeInvalidContactDetailsReportToFile() {
        try {
            for (Map.Entry<Integer, Map<String, String>> entry : contactDetails.entrySet()) {
                int contactID = entry.getKey();
                Map<String, String> errorByFields = entry.getValue();
                for (String fieldName : errorByFields.keySet()) {
                    reportWriter.write("Contact[contact_id=" + contactID + ", " + "error_field=" + fieldName + ", " + "error_message=" + errorByFields.get(fieldName) + "]\r\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void storeInvalidContactsSummaryReportToFile() {
        try {
            for (Map.Entry entry : fieldErrorCounts.entrySet()) {
                reportWriter.write("Contact[field_name=" + entry.getKey() + ", " + "number_of_invalid_contact=" + entry.getValue() + "]\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void storeContactPerStateReportToFile() {
        try {
            reportContents = new TreeMap<>(reportContents); // I want to sort by state
            for (String item : reportContents.keySet()) {
                reportWriter.write("Contact[state_code=" + item + ", " + "number_of_contact=" + reportContents.get(item) + "]\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void storeContactPerAgeGroupReportToFile() {
        try {
            int total = 0;
            for (Integer value : reportContents.values()) {
                total += value;
            }
            // I want to sort by age-group followed the requirement
            String[] ageGroups = {"Children", "Adolescent", "Adult", "Middle Age", "Senior"};
            for (String item : ageGroups) {
                reportWriter.write("Contact[group=" + item + ", " + "number_of_contact=" + reportContents.get(item) + ", " + "percentage_of_contact=" + (int) ((reportContents.get(item) * 100.0f) / total) + "%]\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
