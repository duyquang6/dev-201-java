package vn.kms.launch.cleancode.example;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
@Documented
public @interface MyAnnotation {
    public String name() default "";
}
