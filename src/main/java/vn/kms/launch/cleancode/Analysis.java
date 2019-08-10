// Copyright (c) 2019 KMS Technology, Inc.
package vn.kms.launch.cleancode;

import vn.kms.launch.cleancode.doctype.ContactPerAgeGroup;
import vn.kms.launch.cleancode.doctype.ContactPerState;
import vn.kms.launch.cleancode.model.Contact;
import vn.kms.launch.cleancode.validator.FieldValidation;

import java.io.IOException;
import java.util.*;

import static vn.kms.launch.cleancode.utils.AgeCalculation.convertAgeToGroup;
import static vn.kms.launch.cleancode.utils.ExtractingDocumentInformation.getDocumentName;
import static vn.kms.launch.cleancode.utils.FileHandler.loadFileData;
import static vn.kms.launch.cleancode.utils.HeaderHandler.findHeaderIndexsByFieldNames;
import static vn.kms.launch.cleancode.utils.RowValidation.isRowDataInvalid;

/**
 * Analyzing source data and exporting to result
 *
 * @author quangnguyen
 */

public class Analysis {
    private Map<Integer, Map<String, String>> invalidContacts;
    private Map reports;
    private List<Class> docTypes;
    private Map<String, Integer> fieldErrorCounts;
    private List<Contact> allContacts;

    public Analysis() {
        invalidContacts = new LinkedHashMap<>();
        reports = new HashMap<>();
        fieldErrorCounts = new TreeMap<>();
        allContacts = new ArrayList<>();
    }

    public Map<Integer, Map<String, String>> getInvalidContacts() {
        return invalidContacts;
    }

    public Map getReports() {
        return reports;
    }

    public List<Class> getDocTypes() {
        return docTypes;
    }

    public void setDocTypes(List<Class> docTypes) {
        this.docTypes = docTypes;
    }

    public Map<String, Integer> getFieldErrorCounts() {
        return fieldErrorCounts;
    }

    public List<Contact> getAllContacts() {
        return allContacts;
    }

    private void insertContact(Contact contact) {
        if (contact != null) {
            allContacts.add(contact);
        }
    }

    /**
     * @return All contacts, Invalid contacts, Error fields and report about categorizing
     */
    public void analyzeData(String dataPath) throws IOException, IllegalAccessException {
        // 1. Load data from file
        String payLoad = loadFileData(dataPath);
        String[] lines = payLoad.split("\r\n"); // get all lines
        Map<String, Integer> headerIndexByFieldName = findHeaderIndexsByFieldNames(lines[0]);
        // 2. Basic row validation and map data to contact entities
        for (int currentRowIndex = 0; currentRowIndex < lines.length; currentRowIndex++) {
            String line = lines[currentRowIndex];
            if (isRowDataInvalid(currentRowIndex, line)) {
                continue;
            }
            String[] dataColumns = line.split("\t");
            Contact contact = new Contact();
            contact.loadDataToEntity(headerIndexByFieldName, dataColumns);
            insertContact(contact);
        }
        // 3. Validate contact data
        validateContactDataAndPopOtherField();
        // 4. Sort contact by zipcode and get report about categorizing contact
        Collections.sort(allContacts);
        categorizeContactByState();
        categorizeContactByAgeGroup();
    }

    private void validateContactDataAndPopOtherField() throws IllegalAccessException {
        for (Contact contact : allContacts) {
            // Contact validate data fields
            FieldValidation fieldValidationResult = contact.validate(fieldErrorCounts);
            if (!fieldValidationResult.getErrors().isEmpty()) {
                invalidContacts.put(contact.getId(), fieldValidationResult.getErrors());
            } else {
                // populate other fields from raw fields
                contact.getPerson().setAge();
            }
        }
    }

    /**
     * Categorizing contact by state code and put to report
     */
    private void categorizeContactByState() {
        Map<String, Integer> contactsByStates = new HashMap<>();
        for (Contact contact : allContacts) {
            if (!invalidContacts.containsKey(contact.getId())) {
                int stateCount = 0;
                if (contactsByStates.containsKey(contact.getAddress().getState())) {
                    stateCount = contactsByStates.get(contact.getAddress().getState());
                }
                contactsByStates.put(contact.getAddress().getState(), stateCount + 1);
            }
        }
        reports.put(getDocumentName(ContactPerState.class), contactsByStates);
    }

    // same as categorizeContactByState
    private void categorizeContactByAgeGroup() {
        Map<String, Integer> contactsByAgeGroups = new HashMap<>();
        for (Contact contact : allContacts) {
            if (!invalidContacts.containsKey(contact.getId())) {
                int ageGroupCount = 0;
                if (contactsByAgeGroups.containsKey(convertAgeToGroup(contact.getPerson().getAge()))) {
                    ageGroupCount = contactsByAgeGroups.get(convertAgeToGroup(contact.getPerson().getAge()));
                }
                contactsByAgeGroups.put(convertAgeToGroup(contact.getPerson().getAge()), ageGroupCount + 1);
            }
        }
        reports.put(getDocumentName(ContactPerAgeGroup.class), contactsByAgeGroups);
    }
}