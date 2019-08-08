package vn.kms.launch.cleancode.validator;

import vn.kms.launch.cleancode.annotation.Header;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

import static vn.kms.launch.cleancode.Util.addFieldERROR;

public class FieldValidation {
    private static final Set<String> mValidStateCodes = new HashSet<>(
            Arrays.asList("AL", "AK", "AS", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "GU", "HI", "ID",
                    "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MH", "MA", "MI", "FM", "MN", "MS", "MO", "MT",
                    "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "MP", "OH", "OK", "OR", "PW", "PA", "PR", "RI",
                    "SC", "SD", "TN", "TX", "UT", "VT", "VA", "VI", "WA", "WV", "WI", "WY"));
    private Map<String, String> errors; // errors order by field name

    public FieldValidation() {
        errors = new TreeMap<>();
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    private static void processEmailInvalid(String email, Map<String, String> errors, Map<String, Integer> fieldErrorCounts, String headerValue) {
        errors.put("email", "'" + email + "' is invalid email format");
        addFieldERROR(fieldErrorCounts, headerValue);
    }

//    public void validateObject(Object obj, Map<String, Integer> fieldErrorCounts) throws IllegalAccessException {
//        for (Field field : obj.getClass().getDeclaredFields()) {
//            field.setAccessible(true);
//            for (Annotation validateType : field.getAnnotations()) {
//                String headerValue = field.getAnnotation(Header.class).value();
//                if (validateType instanceof NotEmpty) {
//                    if (field.get(obj).toString().trim().length() == 0) {
//                        errors.put(field.getName(), "is empty");
//                        addFieldERROR(fieldErrorCounts, headerValue);
//                    }
//                } else if (validateType instanceof MaxLength) {
//                    int maxLength = ((MaxLength) validateType).length();
//                    if (field.get(obj).toString().trim().length() > maxLength) {
//                        errors.put(field.getName(), "'" + field.get(obj).toString().trim() + "''s length is over " + maxLength);
//                        addFieldERROR(fieldErrorCounts, headerValue);
//                    }
//                } else if (validateType instanceof NotNull) {
//                    if (field.get(obj) == null) {
//                        errors.put(field.getName(), "'" + field.get(obj).toString() + "' is invalid");
//                        addFieldERROR(fieldErrorCounts, headerValue);
//                    }
//                } else if (validateType instanceof LengthEqual) {
//                    int lengthRequired = ((LengthEqual) validateType).length();
//                    if (field.get(obj).toString().length() != lengthRequired) {
//                        errors.put(field.getName(), "'" + field.get(obj) + "' not equal " + lengthRequired);
//                        addFieldERROR(fieldErrorCounts, headerValue);
//                    }
//                } else if (validateType instanceof PhoneNumberValid) {
//                    String phoneNumber = field.get(obj).toString();
//                    if (!phoneNumber.matches("^\\d{3}\\-\\d{3}\\-\\d{4}$")) {
//                        errors.put(field.getName(), "'" + phoneNumber + "' is invalid format XXX-XXX-XXXX");
//                        addFieldERROR(fieldErrorCounts, headerValue);
//                    }
//                } else if (validateType instanceof StateCodeValid) {
//                    String stateCode = field.get(obj).toString();
//                    if (!mValidStateCodes.contains(stateCode)) {
//                        errors.put(field.getName(), "'" + stateCode + "' is incorrect state code");
//                        addFieldERROR(fieldErrorCounts, headerValue);
//                    }
//                } else if (validateType instanceof ZipCodeValid) {
//                    String zipCode = field.get(obj).toString();
//                    if (!zipCode.matches("^\\d{4,5}$")) {
//                        errors.put(field.getName(), "'" + zipCode + "' is not four or five digits");
//                        addFieldERROR(fieldErrorCounts, headerValue);
//                    }
//                }
//            }
//        }
//    }

    public void validateObject(Object obj, Map<String, Integer> fieldErrorCounts) throws IllegalAccessException {
        Validator validator = new EmailValidator(obj);
        validator.validate(errors,fieldErrorCounts);

        validator = new LengthEqualValidator(obj);
        validator.validate(errors,fieldErrorCounts);

        validator = new MaxLengthValidator(obj);
        validator.validate(errors,fieldErrorCounts);

        validator = new NotEmptyValidator(obj);
        validator.validate(errors,fieldErrorCounts);

        validator = new NotNullValidator(obj);
        validator.validate(errors,fieldErrorCounts);

        validator = new PhoneNumberValidator(obj);
        validator.validate(errors,fieldErrorCounts);

        validator = new StateCodeValidator(obj);
        validator.validate(errors,fieldErrorCounts);

        validator = new ZipCodeValidator(obj);
        validator.validate(errors,fieldErrorCounts);
    }
}