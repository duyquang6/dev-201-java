package vn.kms.launch.cleancode.utils;

import vn.kms.launch.cleancode.common.CommonVariables;
import vn.kms.launch.cleancode.entities.Contact;

import java.util.Map;
import java.util.TreeMap;

public class ValidateContactUtil extends CommonVariables{

    public static void validateContact(Map<String, Integer> counts, Map<Integer, Map<String, String>> contactDetails) {
        for (Contact contact : allContacts) {
            Map<String, String> errors = new TreeMap<>(); // errors order by field name
            if (contact.getFirstName().trim().length() == 0) {
                errors.put("first_name", "is empty");
                addFieldError(counts, "first_name");
            }
            if (contact.getFirstName().length() > 10) {
                errors.put("first_name", "'" + contact.getFirstName() + "''s length is over 10");
                addFieldError(counts, "first_name");
            }
            if (contact.getLastName().trim().length() == 0) {
                errors.put("last_name", "is empty");
                addFieldError(counts, "last_name");
            }
            if (contact.getLastName().length() > 10) {
                errors.put("last_name", "'" + contact.getLastName() + "''s length is over 10");
                addFieldError(counts, "last_name");
            }
            if (contact.getDateOfBirth() == null || contact.getDateOfBirth().trim().length() != 10) {
                errors.put("day_of_birth", "'" + contact.getDateOfBirth() + "' is invalid");
                addFieldError(counts, "day_of_birth");
            }
            if (contact.getAddress().length() > 20) {
                errors.put("address", "'" + contact.getAddress() + "''s length is over 20");
                addFieldError(counts, "address");
            }
            if (contact.getCity().length() > 15) {
                errors.put("city", "'" + contact.getCity() + "''s length is over 15");
                addFieldError(counts, "city");
            }
            if (!VALID_STATE_CODES.contains(contact.getState())) {
                errors.put("state", "'" + contact.getState() + "' is incorrect state code");
                addFieldError(counts, "state");
            }
            if (!contact.getZipCode().matches("^\\d{4,5}$")) {
                errors.put("zip_code", "'" + contact.getZipCode() + "' is not four or five digits");
                addFieldError(counts, "zip_code");
            }
            if (!contact.getMobilePhone().matches("^\\d{3}\\-\\d{3}\\-\\d{4}$")) {
                errors.put("mobile_phone", "'" + contact.getMobilePhone() + "' is invalid format XXX-XXX-XXXX");
                addFieldError(counts, "mobile_phone");
            }
            if (!contact.getEmail().matches("^.+@.+\\..+$")) {
                errors.put("email", "'" + contact.getEmail() + "' is invalid email format");
                addFieldError(counts, "email");
            }

            if (!errors.isEmpty()) {
                contactDetails.put(contact.getId(), errors);
            } else { // populate other fields from raw fields
                contact.getAge();
            }
        }
    }

    private static void addFieldError(Map<String, Integer> counts, String fieldName) {
        Integer count = counts.get(fieldName);
        if (count == null) {
            count = new Integer(0);
        }
        count = count + 1;
        counts.put(fieldName, count);
    }

}
