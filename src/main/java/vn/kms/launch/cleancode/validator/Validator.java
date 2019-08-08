package vn.kms.launch.cleancode.validator;

import vn.kms.launch.cleancode.annotation.Header;
import vn.kms.launch.cleancode.annotation.ValidatorAnnotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

import static vn.kms.launch.cleancode.Util.addFieldERROR;

public abstract class Validator {
    private Object objectToValidate;

    protected Validator(Object objectToValidate) {
        this.objectToValidate = objectToValidate;
    }

    public void validate(Map<String, String> errors, Map<String, Integer> fieldErrorCounts) throws IllegalAccessException {
        for (Field field : objectToValidate.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Annotation annotation = field.getAnnotation(this.getClass().getAnnotation(ValidatorAnnotation.class).type());
            if (annotation != null) {
                String headerValue = field.getAnnotation(Header.class).value();
                String fieldValue = field.get(objectToValidate).toString();
                if (!isValid(field,objectToValidate)) {
                    errors.put(field.getName(), getErrorMsg(fieldValue));
                    addFieldERROR(fieldErrorCounts, headerValue);
                }
            }
        }
    }

    public abstract boolean isValid(Field field, Object objectToValidate) throws IllegalAccessException;

    public abstract String getErrorMsg(String value);
}
