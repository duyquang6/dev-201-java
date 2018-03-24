package vn.kms.launch.cleancode.annotation;

import vn.kms.launch.cleancode.annotation.DobLength;
import vn.kms.launch.cleancode.common.RequiredHandler;

public class DobLengthHandler implements RequiredHandler<DobLength, String> {
    @Override
    public void init(DobLength annotation) {

    }

    @Override
    public boolean isValid(String valueToValidate) {
        if (valueToValidate.trim().length() == 10)//length is 10 
            return true;
        return false;
    }
}
