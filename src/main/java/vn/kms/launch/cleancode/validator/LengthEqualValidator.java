package vn.kms.launch.cleancode.validator;

import vn.kms.launch.cleancode.annotation.ValidatorAnnotation;
import vn.kms.launch.cleancode.annotation.validator.LengthEqual;

import java.lang.reflect.Field;

@ValidatorAnnotation(type = LengthEqual.class)
public class LengthEqualValidator extends Validator {
    private int lengthRequired;

    public LengthEqualValidator(Object objectToValidate) {
        super(objectToValidate);
    }

    @Override
    public boolean isValid(Field field) throws IllegalAccessException {
        lengthRequired = field.getAnnotation(LengthEqual.class).length();
        return field.get(getObjectToValidate()).toString().length() == lengthRequired;
    }

    @Override
    public String getErrorMsg(String fieldValue) {
        return String.format("'%s' not equal %d", fieldValue, lengthRequired);
    }
}
