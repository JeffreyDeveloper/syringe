package me.yushust.inject.resolve.resolver;

import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.token.ContextualTypes;
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
    public Key<?> keyOf(Token<?> declaringClass, Field field) {
        checkNotNull(field);
        Token<?> fieldToken = new Token<>(
                ContextualTypes.resolveContextually(declaringClass, field.getGenericType())
        );
        return getKey(fieldToken, field.getDeclaredAnnotations());
    }

    @Override
    public Key<?> keyOf(Token<?> declaringClass, Parameter parameter) {
        checkNotNull(parameter);
        Token<?> parameterToken = new Token<>(
                ContextualTypes.resolveContextually(declaringClass, parameter.getParameterizedType())
        );
        return getKey(parameterToken, parameter.getDeclaredAnnotations());
    }

    private Key<?> getKey(Token<?> token, Annotation[] declaredAnnotations) {
        for (Annotation annotation : declaredAnnotations) {
            if (annotationTypeHandler.isQualifier(annotation)) {
                if (annotationTypeHandler.isMarkerAnnotation(annotation)) {
                    return Key.of(token, annotation.annotationType(), null);
                }
                return Key.of(token, annotation);
            }
        }
        return Key.of(token);
    }

}
