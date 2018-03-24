package vn.kms.launch.cleancode.annotation;

import vn.kms.launch.cleancode.annotation.NotNull;
import vn.kms.launch.cleancode.common.RequiredHandler;

public class NotNullHandler implements RequiredHandler<NotNull, String> {
    @Override
    public void init(NotNull annotation) {

    }

    @Override
    public boolean isValid(String valueToValidate) {
        if (valueToValidate != null)//not null
            return true;
        return false;
    }
}
