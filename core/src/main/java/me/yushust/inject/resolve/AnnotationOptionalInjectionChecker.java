package me.yushust.inject.resolve;

import me.yushust.inject.Inject;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;

import static me.yushust.inject.internal.Preconditions.checkNotNull;

public class AnnotationOptionalInjectionChecker implements OptionalInjectionChecker {

    @Override
    public boolean isParameterOptional(Parameter parameter) {
        checkNotNull(parameter);
        return hasNullableAnnotation(parameter);
    }

    @Override
    public boolean isFieldOptional(Field field) {
        checkNotNull(field);

        Inject inject = field.getAnnotation(Inject.class);

        if (inject != null && inject.optional()) {
            return true;
        }

        return hasNullableAnnotation(field);
    }

    private boolean hasNullableAnnotation(AnnotatedElement element) {
        for (Annotation annotation : element.getDeclaredAnnotations()) {
            if (annotation.annotationType().getSimpleName().equals("Nullable")) {
                return true;
            }
        }
        return false;
    }

}
