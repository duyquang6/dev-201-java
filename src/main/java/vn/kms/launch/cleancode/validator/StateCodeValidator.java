package vn.kms.launch.cleancode.validator;

import vn.kms.launch.cleancode.annotation.ValidatorAnnotation;
import vn.kms.launch.cleancode.annotation.validator.StateCodeValid;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@ValidatorAnnotation(type = StateCodeValid.class)
public class StateCodeValidator extends Validator {
    private static final Set<String> mValidStateCodes = new HashSet<>(
            Arrays.asList("AL", "AK", "AS", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "GU", "HI", "ID",
                    "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MH", "MA", "MI", "FM", "MN", "MS", "MO", "MT",
                    "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "MP", "OH", "OK", "OR", "PW", "PA", "PR", "RI",
                    "SC", "SD", "TN", "TX", "UT", "VT", "VA", "VI", "WA", "WV", "WI", "WY"));

    public StateCodeValidator(Object objectToValidate) {
        super(objectToValidate);
    }

    @Override
    public boolean isValid(Field field) throws IllegalAccessException {
        return mValidStateCodes.contains(field.get(getObjectToValidate()).toString());
    }

    @Override
    public String getErrorMsg(String fieldValue) {
        return String.format("'%s' is incorrect state code", fieldValue);
    }
}
