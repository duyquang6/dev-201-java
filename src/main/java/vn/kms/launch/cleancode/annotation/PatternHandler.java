package vn.kms.launch.cleancode.annotation;

import vn.kms.launch.cleancode.annotation.Pattern;
import vn.kms.launch.cleancode.common.RequiredHandler;

public class PatternHandler implements RequiredHandler<Pattern, String> {
    String patternToValidate;

    @Override
    public void init(Pattern pattern) {
        patternToValidate = pattern.value();
    }

    @Override
    public boolean isValid(String valueToValidate) {
        if (valueToValidate.matches(patternToValidate))
            return true;
        return false;
    }
}
