package vn.kms.launch.cleancode.validator;

import vn.kms.launch.cleancode.annotation.ValidatorAnnotation;
import vn.kms.launch.cleancode.annotation.validator.PhoneNumberValid;

import java.lang.reflect.Field;

@ValidatorAnnotation(type = PhoneNumberValid.class)
public class PhoneNumberValidator extends Validator {

    public PhoneNumberValidator(Object objectToValidate) {
        super(objectToValidate);
    }

    @Override
    public boolean isValid(Field field, Object objectToValidate) throws IllegalAccessException {
        return field.get(objectToValidate).toString().matches("^\\d{3}\\-\\d{3}\\-\\d{4}$");
    }

    @Override
    public String getErrorMsg(String fieldValue) {
        return String.format("'%s' is invalid format XXX-XXX-XXXX", fieldValue);
    }
}
