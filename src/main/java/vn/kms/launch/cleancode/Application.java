package vn.kms.launch.cleancode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class Application {
  private static final Set<String> mValidStateCodes = new HashSet<>(
          Arrays.asList("AL", "AK", "AS", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "GU", "HI", "ID",
                  "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MH", "MA", "MI", "FM", "MN", "MS", "MO", "MT",
                  "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "MP", "OH", "OK", "OR", "PW", "PA", "PR", "RI",
                  "SC", "SD", "TN", "TX", "UT", "VT", "VA", "VI", "WA", "WV", "WI", "WY"));
  private static final Map<String, String> reportHeaders = new HashMap<String, String>() {
    {
      put("invalid-contact-details", "contact_id\terror_field\terror_message\r\n");
      put("invalid-contact-summary", "field_name\tnumber_of_invalid_contact\r\n");
      put("contact-per-state", "state_code\tnumber_of_contact\r\n");
      put("contact-per-age-group", "group\tnumber_of_contact\tpercentage_of_contact\r\n");
    }
  };
  public static void main(String[] args) throws Exception {
    Map<String, Integer> fieldErrorCounts = new TreeMap<>(); // I want to sort by key
    Map<Integer, Map<String, String>> invalidContacts = new LinkedHashMap<>(); // invalidContacts order by ID
    Map reports = new HashMap<>();
    storeReports(fieldErrorCounts,invalidContacts,reports);
  }

  static File openOrCreateFolder(String folderPath) {
    File outputFolder = new File(folderPath);
    if (!outputFolder.exists()) {
      outputFolder.mkdirs();
    }
    return outputFolder;
  }
  static void storeReports(Map<String, Integer> fieldErrorCounts, Map<Integer, Map<String, String>> invalidContacts, Map reports) {
    for (String reportName : reportHeaders.keySet()) {
      File outputFolder = openOrCreateFolder("output");
      Writer writer = null;
      try {
        writer = new FileWriter(new File(outputFolder, reportName + ".tab"));
        // write header
        writer.write(reportHeaders.get(reportName));
        Map<String, Integer> report = (Map<String, Integer>) reports.get(reportName);
        if (reportName.equals("contact-per-age-group")) {
          int total = 0;
          for (Integer v : report.values()) {
            total += v;
          }
          // I want to sort by age-group followed the requirement
          String[] ageGroups = { "Children", "Adolescent", "Adult", "Middle Age", "Senior" };
          for (String item : ageGroups) {
            writer.write(item + "\t" + report.get(item) + "\t" + (int) ((report.get(item) * 100.0f) / total)
                    + "%\r\n");
          }
        } else if (reportName.equals("contact-per-state")) {
          report = new TreeMap<>(report); // I want to sort by state
          for (String item : report.keySet()) {
            writer.write(item + "\t" + report.get(item) + "\r\n");
          }
        } else if (reportName.equals("invalid-contact-summary")) {
          for (Map.Entry entry : fieldErrorCounts.entrySet()) {
            writer.write(entry.getKey() + "\t" + entry.getValue() + "\r\n");
          }
        } else { // invalid-contact-details
          for (Map.Entry<Integer, Map<String, String>> entry : invalidContacts.entrySet()) {
            int contactID = entry.getKey();
            Map<String, String> errorByFields = entry.getValue();
            for (String field_name : errorByFields.keySet()) {
              writer.write(contactID + "\t" + field_name + "\t" + errorByFields.get(field_name) + "\r\n");
            }
          }
        }
        writer.flush();
        System.out.println("Generated report " + "output/" + reportName + ".tab");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  void loadFile() {

  }
  void convertToEntity() {

  }
  void validateEntity() {

  }
  void storeEntity() {

  }
  void returnReports() {

  }
}
