package vn.kms.launch.cleancode.annotation;

import vn.kms.launch.cleancode.common.Required;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
@Required(handleBy = ValidStateCodesHandler.class)
@Inherited
public @interface ValidStateCodes {
    String value() default "";
    String message() default "State codes is incorrect.";
}
