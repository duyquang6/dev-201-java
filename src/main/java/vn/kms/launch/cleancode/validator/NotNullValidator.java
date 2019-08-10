package vn.kms.launch.cleancode.validator;

import vn.kms.launch.cleancode.annotation.ValidatorAnnotation;
import vn.kms.launch.cleancode.annotation.validator.NotNull;

import java.lang.reflect.Field;

@ValidatorAnnotation(type = NotNull.class)
public class NotNullValidator extends Validator {

    public NotNullValidator(Object objectToValidate) {
        super(objectToValidate);
    }

    @Override
    public boolean isValid(Field field, Object objectToValidate) throws IllegalAccessException {
        return field.get(objectToValidate) != null;
    }

    @Override
    public String getErrorMsg(String fieldValue) {
        return String.format("'%s' is null", fieldValue);
    }
}
