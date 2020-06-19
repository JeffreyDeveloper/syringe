package me.yushust.inject.resolve;

import me.yushust.inject.BindingAnnotation;

import javax.inject.Qualifier;
import java.lang.annotation.Annotation;

import static me.yushust.inject.internal.Preconditions.checkNotNull;

public class AnnotationTypeHandler {

    public boolean isQualifier(Annotation annotation) {
        checkNotNull(annotation);

        Class<? extends Annotation> annotationType = annotation.annotationType();

        if (annotationType.isAnnotationPresent(Qualifier.class)) {
            return true;
        }

        return annotationType.isAnnotationPresent(BindingAnnotation.class);
    }

}
