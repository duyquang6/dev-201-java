package vn.kms.launch.cleancode.validator;

import vn.kms.launch.cleancode.annotation.ValidatorAnnotation;
import vn.kms.launch.cleancode.annotation.validator.NotEmpty;

import java.lang.reflect.Field;

@ValidatorAnnotation(type = NotEmpty.class)
public class NotEmptyValidator extends Validator {

    protected NotEmptyValidator(Object objectToValidate) {
        super(objectToValidate);
    }

    @Override
    public boolean isValid(Field field, Object objectToValidate) throws IllegalAccessException {
        return field.get(objectToValidate).toString().trim().length() != 0;
    }

    @Override
    public String getErrorMsg(String fieldValue) {
        return String.format("%s is empty", fieldValue);
    }
}
