package vn.kms.launch.cleancode.annotation;

import vn.kms.launch.cleancode.common.Required;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Required(handleBy = NotNullHandler.class)
@Inherited
public @interface NotNull {
    boolean value() default true;
    String message() default "Date of birth is not null";
}
