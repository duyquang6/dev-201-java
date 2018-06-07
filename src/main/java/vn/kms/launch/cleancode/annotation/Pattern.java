package vn.kms.launch.cleancode.annotation;

import vn.kms.launch.cleancode.common.Required;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
@Required(handleBy = PatternHandler.class)
@Inherited
public @interface Pattern {
    String value() default "";
    String message() default "Do not leave field empty.";
}
