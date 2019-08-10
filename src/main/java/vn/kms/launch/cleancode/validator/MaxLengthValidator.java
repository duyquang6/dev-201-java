package vn.kms.launch.cleancode.validator;

import vn.kms.launch.cleancode.annotation.ValidatorAnnotation;
import vn.kms.launch.cleancode.annotation.validator.MaxLength;

import java.lang.reflect.Field;

@ValidatorAnnotation(type = MaxLength.class)
public class MaxLengthValidator extends Validator {
    private int maxLength;

    public MaxLengthValidator(Object objectToValidate) {
        super(objectToValidate);
    }

    @Override
    public boolean isValid(Field field, Object objectToValidate) throws IllegalAccessException {
        maxLength = field.getAnnotation(MaxLength.class).length();
        return field.get(objectToValidate).toString().length() <= maxLength;
    }

    @Override
    public String getErrorMsg(String fieldValue) {
        return String.format("'%s''s length is over %d", fieldValue.trim(), maxLength);
    }
}
