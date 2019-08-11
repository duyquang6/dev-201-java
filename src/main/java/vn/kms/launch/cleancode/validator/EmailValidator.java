package vn.kms.launch.cleancode.validator;

import vn.kms.launch.cleancode.annotation.ValidatorAnnotation;
import vn.kms.launch.cleancode.annotation.validator.EmailValid;

import java.lang.reflect.Field;

@ValidatorAnnotation(type = EmailValid.class)
public class EmailValidator extends Validator {

    public EmailValidator(Object objectToValidate) {
        super(objectToValidate);
    }

    @Override
    public boolean isValid(Field field) throws IllegalAccessException {
        return field.get(getObjectToValidate()).toString().matches("^.+@.+\\..+$");
    }

    @Override
    public String getErrorMsg(String email) {
        return String.format("'%s' is invalid email format", email);
    }


}
