package vn.kms.launch.cleancode.common;

import java.lang.annotation.Annotation;

public interface RequiredHandler<A extends Annotation, V> {
    void init(A annotation);
    boolean isValid(V valueToValidate);
}
