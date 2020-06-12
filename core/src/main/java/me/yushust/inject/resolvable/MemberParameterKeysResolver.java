package me.yushust.inject.resolvable;

import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.token.Token;
import me.yushust.inject.identity.token.Types;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MemberParameterKeysResolver implements ParameterKeysResolver {

    private final AnnotationTypeHandler annotationTypeHandler;

    public MemberParameterKeysResolver(AnnotationTypeHandler annotationTypeHandler) {
        Objects.requireNonNull(annotationTypeHandler);
        this.annotationTypeHandler = annotationTypeHandler;
    }

    @Override
    public List<Key<?>> resolveParameterKeys(Member methodOrConstructor) {

        Objects.requireNonNull(methodOrConstructor);

        List<Key<?>> parameterTypes = new ArrayList<>();

        Executable executable = null;

        if (methodOrConstructor instanceof Method || methodOrConstructor instanceof Constructor) {
            executable = (Executable) methodOrConstructor;
        }

        if (executable == null) {
            throw new IllegalArgumentException("The parameter must be a method or a constructor!");
        }


        for (Parameter parameter : executable.getParameters()) {
            parameterTypes.add(getParameterKey(parameter));
        }

        return parameterTypes;

    }

    @Override
    public <T> Key<T> getParameterKey(Parameter parameter) {
        Token<T> parameterToken = new Token<>(Types.wrap(parameter.getParameterizedType()));

        for (Annotation annotation : parameter.getDeclaredAnnotations()) {
            if (annotationTypeHandler.isLinkingAnnotation(annotation)) {
                return Key.of(parameterToken, annotation);
            }
        }

        return Key.of(parameterToken);

    }

}
