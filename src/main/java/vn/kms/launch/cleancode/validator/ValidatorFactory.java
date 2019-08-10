package vn.kms.launch.cleancode.validator;

import java.util.ArrayList;
import java.util.List;

public class ValidatorFactory {
    private ValidatorFactory() {
    }

    public static List<Validator> getValidatorList(Object obj) {
        ArrayList<Validator> validators = new ArrayList<>();
        validators.add(new EmailValidator(obj));
        validators.add(new LengthEqualValidator(obj));
        validators.add(new MaxLengthValidator(obj));
        validators.add(new NotEmptyValidator(obj));
        validators.add(new NotNullValidator(obj));
        validators.add(new PhoneNumberValidator(obj));
        validators.add(new StateCodeValidator(obj));
        validators.add(new ZipCodeValidator(obj));
        return validators;
    }
}
