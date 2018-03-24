package vn.kms.launch.cleancode.annotation;

import vn.kms.launch.cleancode.common.RequiredHandler;
import vn.kms.launch.cleancode.annotation.ValidLength;

public class ValidLengthHandler implements RequiredHandler<ValidLength, String> {

    private int maxLength;

    @Override
    public void init(ValidLength max) {
        maxLength = max.value();
    }

    @Override
    public boolean isValid(String valueToValidate) {
        if (valueToValidate.length() <= maxLength)
            return true; 
        return false;
    }
}
