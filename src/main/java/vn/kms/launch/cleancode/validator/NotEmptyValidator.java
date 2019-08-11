package vn.kms.launch.cleancode.validator;

import vn.kms.launch.cleancode.annotation.ValidatorAnnotation;
import vn.kms.launch.cleancode.annotation.validator.NotEmpty;

import java.lang.reflect.Field;

@ValidatorAnnotation(type = NotEmpty.class)
public class NotEmptyValidator extends Validator {

    public NotEmptyValidator(Object objectToValidate) {
        super(objectToValidate);
    }

    @Override
    public boolean isValid(Field field) throws IllegalAccessException {
        return field.get(getObjectToValidate()).toString().trim().length() != 0;
    }

    @Override
    public String getErrorMsg(String fieldValue) {
        return String.format("%s is empty", fieldValue);
    }
}
