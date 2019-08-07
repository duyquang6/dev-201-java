package vn.kms.launch.cleancode;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

import static vn.kms.launch.cleancode.Util.*;
import static vn.kms.launch.cleancode.Util.convertAgeToGroup;

public class AnalysisResult {
    private static final Set<String> mValidStateCodes = new HashSet<>(
            Arrays.asList("AL", "AK", "AS", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "GU", "HI", "ID",
                    "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MH", "MA", "MI", "FM", "MN", "MS", "MO", "MT",
                    "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "MP", "OH", "OK", "OR", "PW", "PA", "PR", "RI",
                    "SC", "SD", "TN", "TX", "UT", "VT", "VA", "VI", "WA", "WV", "WI", "WY"));
    private static final int MAX_COLUMN = 14;
    private Map<Integer, Map<String, String>> invalidContacts;
    private Map<String, Integer> fieldErrorCounts;
    private Map reports;
    private List<Class> reportTypes;

    public AnalysisResult() {
        invalidContacts = new LinkedHashMap<>();
        fieldErrorCounts = new TreeMap<>();
        reports = new HashMap<>();
    }

    public Map<Integer, Map<String, String>> getInvalidContacts() {
        return invalidContacts;
    }

    public Map<String, Integer> getFieldErrorCounts() {
        return fieldErrorCounts;
    }

    public Map getReports() {
        return reports;
    }

    public void setReportTypes(List<Class> reportTypes) {
        this.reportTypes = reportTypes;
    }

    public void exportFile() {
        for (Class reportType : reportTypes) {
            String reportName = getReportName(reportType);
            File outputFolder = openOrCreateFolder("output");
            try (Writer writer = openFileWriter(outputFolder,reportName,"tab")) {
                writeHeader(writer, reportType);
                writeDocByReportName(writer, reportName, this);
                writer.flush();
                System.out.println("Generated report " + "output/" + reportName + ".tab");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
     *         invalid-entities, total-errors
     *         loadFileAndConvertToEntityAndValidateEntityAndStoreEntityAndReturnReports
     */
    public void analyzeData(String dataPath) throws Exception {
        String payLoad = loadFileDataToString(dataPath);
        String[] lines = payLoad.split("\r"); // get all lines
        List<Contact> allContacts = new ArrayList<>();
        Map<String, Integer> headerIndexByFieldName = findIndexsByFieldNames(lines[0]);

        for (int currentRowIndex = 0; currentRowIndex < lines.length; currentRowIndex++) {
            String line = lines[currentRowIndex];
            if (isRowDataInvalid(currentRowIndex, line)) {
                continue;
            }
            String[] dataColumns = line.split("\t");
            // TODO: I will use reflection & annotations to build contact object later
            Person person = new Person();
            Address address = new Address();
            Contact contact = new Contact(person, address);
            contact.loadDataToEntity(headerIndexByFieldName, dataColumns);
            insertContact(contact, allContacts);
        }

        // 2. Validate contact data
        validateContactData(allContacts);
        // 3. Sort contact by zipcode
        // TODO: need to change this code if we use other field to sort the list
        sortByZipCode(allContacts);
        Field testField = InvalidContactSummaryDoc.class.getDeclaredField("numberOfInvalidContact");
        Column annotatedTestField = testField.getAnnotation(Column.class);
        System.out.println(annotatedTestField.header());

        exportValidDataToFile(allContacts);
        genContactByStateAndByAgeGroup(allContacts);
    }

    private void validateContactData(List<Contact> allContacts) {
        // TODO: I will use refection & annotations to isValid contact data later
        for (Contact contact : allContacts) {
            Map<String, String> errors = new TreeMap<>(); // errors order by field name
            if (contact.getPerson().getFirstName().trim().length() == 0) {
                errors.put("firstName", "is empty");
                addFieldERROR(fieldErrorCounts, "first_name");
            }
            if (contact.getPerson().getFirstName().length() > 10) {
                errors.put("firstName", "'" + contact.getPerson().getFirstName() + "''s length is over 10");
                addFieldERROR(fieldErrorCounts, "first_name");
            }
            if (contact.getPerson().getLastName().trim().length() == 0) {
                errors.put("lastName", "is empty");
                addFieldERROR(fieldErrorCounts, "last_name");
            }
            if (contact.getPerson().getLastName().length() > 10) {
                errors.put("lastName", "'" + contact.getPerson().getLastName() + "''s length is over 10");
                addFieldERROR(fieldErrorCounts, "last_name");
            }
            if (contact.getPerson().getDayOfBirth() == null || contact.getPerson().getDayOfBirth().trim().length() != 10) {
                errors.put("day_of_birth", "'" + contact.getPerson().getDayOfBirth() + "' is invalid");
                addFieldERROR(fieldErrorCounts, "day_of_birth");
            }
            if (contact.getAddress().getHouseNumberAndStreet().length() > 20) {
                errors.put("address", "'" + contact.getAddress().getHouseNumberAndStreet() + "''s length is over 20");
                addFieldERROR(fieldErrorCounts, "address");
            }
            if (contact.getAddress().getCity().length() > 15) {
                errors.put("city", "'" + contact.getAddress().getCity() + "''s length is over 15");
                addFieldERROR(fieldErrorCounts, "city");
            }
            if (!mValidStateCodes.contains(contact.getAddress().getState())) {
                errors.put("state", "'" + contact.getAddress().getState() + "' is incorrect state code");
                addFieldERROR(fieldErrorCounts, "state");
            }
            if (!contact.getAddress().getZipCode().matches("^\\d{4,5}$")) {
                errors.put("zipCode", "'" + contact.getAddress().getZipCode() + "' is not four or five digits");
                addFieldERROR(fieldErrorCounts, "zip_code");
            }
            if (!contact.getPhoneNumber().matches("^\\d{3}\\-\\d{3}\\-\\d{4}$")) {
                errors.put("mobilePhone", "'" + contact.getPhoneNumber() + "' is invalid format XXX-XXX-XXXX");
                addFieldERROR(fieldErrorCounts, "mobile_phone");
            }
            if (!contact.getEmail().matches("^.+@.+\\..+$")) {
                errors.put("email", "'" + contact.getEmail() + "' is invalid email format");
                addFieldERROR(fieldErrorCounts, "email");
            }

            if (!errors.isEmpty()) {
                invalidContacts.put(contact.getId(), errors);
            } else { // populate other fields from raw fields
                contact.getPerson().setAge(calculateAgeByYear(contact.getPerson().getDayOfBirth()));
            }
        }
    }

    private void sortByZipCode(List<Contact> allContacts) {
        for (int i = 0; i < allContacts.size(); i++) {
            for (int j = allContacts.size() - 1; j > i; j--) {
                Contact contactA = allContacts.get(i);
                Contact contactB = allContacts.get(j);
                if (!invalidContacts.containsKey(contactA.getId())
                        && !invalidContacts.containsKey(contactB.getId())) {
                    int zipCodeContactA = Integer.parseInt(contactA.getAddress().getZipCode());
                    int zipCodeContactB = Integer.parseInt(contactB.getAddress().getZipCode());
                    if (zipCodeContactA > zipCodeContactB) {
                        allContacts.set(i, contactB);
                        allContacts.set(j, contactA);
                    }
                }
            }
        }
    }

    private void exportValidDataToFile(List<Contact> allContacts) {
        File outputFile = new File("output");
        if (!outputFile.exists()) {
            outputFile.mkdirs();
        }

        try(Writer writer = new FileWriter(new File(outputFile, "valid-contacts.tab"))) {
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
            System.out.println("Storing valid data got error");
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
        Map<String,String> contactHeaders = getFieldsByHeaders(Contact.class);
        Map<String,String> personHeaders = getFieldsByHeaders(Person.class);
        Map<String,String> addressHeaders = getFieldsByHeaders(Address.class);

        ArrayList<String> headers = new ArrayList<String>();
        headers.addAll(contactHeaders.values());
        headers.addAll(personHeaders.values());
        headers.addAll(addressHeaders.values());

        Map<String, Integer> headerIndexByFieldName = new HashMap<String, Integer>();
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