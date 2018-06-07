package vn.kms.launch.cleancode.annotation;

import vn.kms.launch.cleancode.common.Required;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
@Required(handleBy = ValidLengthHandler.class)
@Inherited
public @interface ValidLength {
    int value() default 255;
    String message() default "Field length is not over expected length.";
}
