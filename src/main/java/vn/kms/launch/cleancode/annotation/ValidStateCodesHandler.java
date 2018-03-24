package vn.kms.launch.cleancode.annotation;

import vn.kms.launch.cleancode.common.RequiredHandler;
import vn.kms.launch.cleancode.annotation.ValidStateCodes;
import vn.kms.launch.cleancode.common.CommonVariables;

public class ValidStateCodesHandler extends CommonVariables implements RequiredHandler<ValidStateCodes, String> {

    private String stateCodesToValid;

    @Override
    public void init(ValidStateCodes stateCodes) {
        stateCodesToValid = stateCodes.value();
    }

    @Override
    public boolean isValid(String valueToValidate) {
        if (VALID_STATE_CODES.contains(stateCodesToValid))//state code is exist 
            return true;
        return false;
    }
}
