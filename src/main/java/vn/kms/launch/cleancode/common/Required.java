package vn.kms.launch.cleancode.common;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.ANNOTATION_TYPE)
@Inherited
public @interface Required {
    //create Required annotation inherited from RequiredHandler interface
    Class<? extends RequiredHandler<?, ?>> handleBy();
}
