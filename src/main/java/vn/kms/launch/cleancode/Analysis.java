package vn.kms.launch.cleancode;

import vn.kms.launch.cleancode.model.Address;
import vn.kms.launch.cleancode.model.Contact;
import vn.kms.launch.cleancode.model.Person;
import vn.kms.launch.cleancode.validator.FieldValidation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import static vn.kms.launch.cleancode.Util.*;

public class Analysis {
    private static final int MAX_COLUMN = 14;
    private static final int YEAR_OF_REPORT = 2016;
    private Map<Integer, Map<String, String>> invalidContacts;
    private Map reports;
    private List<Class> reportTypes;
    private FieldValidation fieldValidationResult;
    private Map<String, Integer> fieldErrorCounts;

    public Analysis() {
        invalidContacts = new LinkedHashMap<>();
        reports = new HashMap<>();
        fieldErrorCounts = new TreeMap<>();
    }

    public Map<Integer, Map<String, String>> getInvalidContacts() {
        return invalidContacts;
    }

    public Map getReports() {
        return reports;
    }

    public List<Class> getReportTypes() {
        return reportTypes;
    }

    public void setReportTypes(List<Class> reportTypes) {
        this.reportTypes = reportTypes;
    }

    public Map<String, Integer> getFieldErrorCounts() {
        return fieldErrorCounts;
    }

    public FieldValidation getFieldValidationResult() {
        return fieldValidationResult;
    }

    private boolean isCurrentRowHeader(int rowIndex) {
        return rowIndex == 0;
    }

    private boolean isRowBlank(String line) {
        int countCharInLine = line.trim().length();
        return countCharInLine == 0;
    }

    private boolean isContainBlankField(String[] dataColumns) {
        return dataColumns.length != MAX_COLUMN;
    }

    private boolean isRowDataInvalid(int rowIndex, String line) {
        String[] dataColumns = line.split("\t");
        return isCurrentRowHeader(rowIndex) ||
                isRowBlank(line) ||
                isContainBlankField(dataColumns);
    }

    private void insertContact(Contact contact, List<Contact> allContacts) {
        if (contact != null) {
            allContacts.add(contact);
        } else {
            // for some reason, I think contact object may be null (I am not sure, but I
            // want to log for sure!!!)
            System.out.println("Contact is null, don't know why!!!");
        }
    }

    /**
     * @return array of total-lines, blank-lines, invalid-lines, valid-entities,
     * invalid-entities, total-errors
     * loadFileAndConvertToEntityAndValidateEntityAndStoreEntityAndReturnReports
     */
    public void analyzeData(String dataPath) throws IOException, IllegalAccessException {
        String payLoad = loadFileData(dataPath);
        String[] lines = payLoad.split("\r\n"); // get all lines
        List<Contact> allContacts = new ArrayList<>();
        Map<String, Integer> headerIndexByFieldName = findIndexsByFieldNames(lines[0]);

        for (int currentRowIndex = 0; currentRowIndex < lines.length; currentRowIndex++) {
            String line = lines[currentRowIndex];
            if (isRowDataInvalid(currentRowIndex, line)) {
                continue;
            }
            String[] dataColumns = line.split("\t");
            Contact contact = new Contact();
            contact.loadDataToEntity(headerIndexByFieldName, dataColumns);
            insertContact(contact, allContacts);
        }

        // 2. Validate contact data
        validateContactData(allContacts);
        // 3. Sort contact by zipcode
        Collections.sort(allContacts);

        exportValidDataToFile(allContacts);
        genContactByStateAndByAgeGroup(allContacts);
    }

    private void validateContactData(List<Contact> allContacts) throws IllegalAccessException {
        for (Contact contact : allContacts) {
            fieldValidationResult = contact.validate(fieldErrorCounts);
            if (!fieldValidationResult.getErrors().isEmpty()) {
                invalidContacts.put(contact.getId(), fieldValidationResult.getErrors());
            } else {
                // populate other fields from raw fields
                contact.getPerson().calculateAgeByYear(YEAR_OF_REPORT);
            }
        }
    }

    private void exportValidDataToFile(List<Contact> allContacts) {
        File outputFile = new File("output");
        if (!outputFile.exists()) {
            outputFile.mkdirs();
        }

        try (Writer writer = new FileWriter(new File(outputFile, "valid-contacts.tab"))) {
            // write header
            writer.write(
                    "id\tfirst_name\tlast_name\tday_of_birth\taddress\tcity\tstate\tzip_code\tmobile_phone\temail\r\n");
            for (Contact contact : allContacts) {
                if (!invalidContacts.containsKey(contact.getId())) {
                    writer.write(contact.toString() + "\r\n");
                }
            }
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void genContactByStateAndByAgeGroup(List<Contact> allContacts) {
        reports = new HashMap<>();
        Map<String, Integer> contactsByStates = new HashMap<>();
        Map<String, Integer> contactsByAgeGroups = new HashMap<>();
        for (Contact contact : allContacts) {
            if (!invalidContacts.containsKey(contact.getId())) {
                int stateCount = 0;
                if (contactsByStates.containsKey(contact.getAddress().getState())) {
                    stateCount = contactsByStates.get(contact.getAddress().getState());
                }
                contactsByStates.put(contact.getAddress().getState(), stateCount + 1);

                int ageGroupCount = 0;
                if (contactsByAgeGroups.containsKey(convertAgeToGroup(contact.getPerson().getAge()))) {
                    ageGroupCount = contactsByAgeGroups.get(convertAgeToGroup(contact.getPerson().getAge()));
                }
                contactsByAgeGroups.put(convertAgeToGroup(contact.getPerson().getAge()), ageGroupCount + 1);
            }
        }
        reports.put("contact-per-state", contactsByStates);
        reports.put("contact-per-age-group", contactsByAgeGroups);
    }

    private Map<String, Integer> findIndexsByFieldNames(String headerLine) {
        ArrayList<String> contactHeaders = getColumnHeaders(Contact.class);
        ArrayList<String> personHeaders = getColumnHeaders(Person.class);
        ArrayList<String> addressHeaders = getColumnHeaders(Address.class);

        ArrayList<String> headers = new ArrayList<>();
        headers.addAll(contactHeaders);
        headers.addAll(personHeaders);
        headers.addAll(addressHeaders);

        Map<String, Integer> headerIndexByFieldName = new HashMap<>();
        for (String header : headers) {
            int headerIndex = findHeaderIndex(headerLine, header);
            headerIndexByFieldName.put(header, headerIndex);
        }
        return headerIndexByFieldName;
    }

    private int findHeaderIndex(String line, String header) {
        String[] headers = line.split("\t");
        return Arrays.asList(headers).indexOf(header);
    }
}