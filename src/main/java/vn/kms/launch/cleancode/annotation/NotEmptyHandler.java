package vn.kms.launch.cleancode.annotation;

import vn.kms.launch.cleancode.annotation.NotEmpty;
import vn.kms.launch.cleancode.common.RequiredHandler;

public class NotEmptyHandler implements RequiredHandler<NotEmpty, String> {
    @Override
    public void init(NotEmpty annotation) {

    }

    @Override
    public boolean isValid(String valueToValidate) {
        if (valueToValidate.trim().length() != 0)//not empty 
            return true;
        return false;
    }
}
