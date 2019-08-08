package vn.kms.launch.cleancode.validator;

import vn.kms.launch.cleancode.annotation.ValidatorAnnotation;
import vn.kms.launch.cleancode.annotation.validator.EmailValid;

import java.lang.reflect.Field;

@ValidatorAnnotation(type = EmailValid.class)
public class EmailValidator extends Validator {

    protected EmailValidator(Object objectToValidate) {
        super(objectToValidate);
    }

    @Override
    public boolean isValid(Field field, Object objectToValidate) throws IllegalAccessException {
        return field.get(objectToValidate).toString().matches("^.+@.+\\..+$");
    }

    @Override
    public String getErrorMsg(String email) {
        return String.format("'%s' is invalid email format",email);
    }


}
