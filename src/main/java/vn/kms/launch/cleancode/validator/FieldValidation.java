package vn.kms.launch.cleancode.validator;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static vn.kms.launch.cleancode.validator.ValidatorFactory.getValidatorList;

public class FieldValidation {
    // errors order by field name
    private Map<String, String> errors;

    public FieldValidation() {
        errors = new TreeMap<>();
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void validateObject(Object obj, Map<String, Integer> fieldErrorCounts) throws IllegalAccessException {
        List<Validator> validators = getValidatorList(obj);
        for (Validator validator : validators) {
            validator.validateAndExportToVariable(errors, fieldErrorCounts);
        }
    }
}