package vn.kms.launch.cleancode.annotation;

import vn.kms.launch.cleancode.common.Required;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Required(handleBy = NotEmptyHandler.class)
@Inherited
public @interface NotEmpty {
    boolean value() default true;
    String message() default "Do not leave field empty.";
}
