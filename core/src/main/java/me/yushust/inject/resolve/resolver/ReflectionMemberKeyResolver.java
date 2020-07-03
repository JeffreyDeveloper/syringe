package me.yushust.inject.resolve.resolver;

import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.token.ContextualTypes;
import me.yushust.inject.identity.token.TypeReference;
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
    public Key<?> keyOf(TypeReference<?> declaringClass, Field field) {
        checkNotNull(field);
        TypeReference<?> fieldTypeReference = TypeReference.of(
                ContextualTypes.resolveContextually(declaringClass, field.getGenericType())
        );
        return getKey(fieldTypeReference, field.getDeclaredAnnotations());
    }

    @Override
    public Key<?> keyOf(TypeReference<?> declaringClass, Parameter parameter) {
        checkNotNull(parameter);
        TypeReference<?> parameterTypeReference = TypeReference.of(
                ContextualTypes.resolveContextually(declaringClass, parameter.getParameterizedType())
        );
        return getKey(parameterTypeReference, parameter.getDeclaredAnnotations());
    }

    private Key<?> getKey(TypeReference<?> typeReference, Annotation[] declaredAnnotations) {
        for (Annotation annotation : declaredAnnotations) {
            if (annotationTypeHandler.isQualifier(annotation)) {
                if (annotationTypeHandler.isMarkerAnnotation(annotation)) {
                    return Key.of(typeReference, annotation.annotationType(), null);
                }
                return Key.of(typeReference, annotation);
            }
        }
        return Key.of(typeReference);
    }

}
