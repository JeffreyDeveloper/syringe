package me.yushust.inject.resolve.resolver;

import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.token.Token;
import me.yushust.inject.resolve.AnnotationTypeHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;

import static me.yushust.inject.internal.Preconditions.checkNotNull;

public class ReflectionMemberKeyResolver implements MemberKeyResolver {

    private final AnnotationTypeHandler annotationTypeHandler;

    public ReflectionMemberKeyResolver(AnnotationTypeHandler annotationTypeHandler) {
        this.annotationTypeHandler = checkNotNull(annotationTypeHandler);
    }

    @Override
    public Key<?> keyOf(Field field) {
        checkNotNull(field);

        Token<?> fieldToken = new Token<>(field.getGenericType());

        for (Annotation annotation : field.getDeclaredAnnotations()) {
            if (annotationTypeHandler.isQualifier(annotation)) {
                return Key.of(fieldToken, annotation);
            }
        }

        return Key.of(fieldToken);
    }

    @Override
    public Key<?> keyOf(Parameter parameter) {
        Token<?> parameterToken = new Token<>(parameter.getParameterizedType());

        for (Annotation annotation : parameter.getDeclaredAnnotations()) {
            if (annotationTypeHandler.isQualifier(annotation)) {
                return Key.of(parameterToken, annotation);
            }
        }

        return Key.of(parameterToken);
    }

}
