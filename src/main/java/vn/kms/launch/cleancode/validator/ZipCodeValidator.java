package vn.kms.launch.cleancode.validator;

import vn.kms.launch.cleancode.annotation.ValidatorAnnotation;
import vn.kms.launch.cleancode.annotation.validator.ZipCodeValid;

import java.lang.reflect.Field;

@ValidatorAnnotation(type = ZipCodeValid.class)
public class ZipCodeValidator extends Validator {

    public ZipCodeValidator(Object objectToValidate) {
        super(objectToValidate);
    }

    @Override
    public boolean isValid(Field field, Object objectToValidate) throws IllegalAccessException {
        return field.get(objectToValidate).toString().matches("^\\d{4,5}$");
    }

    @Override
    public String getErrorMsg(String fieldValue) {
        return String.format("'%s' is not four or five digits", fieldValue);
    }
}
