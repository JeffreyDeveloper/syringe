package me.yushust.inject.resolvable;

import me.yushust.inject.LinkingAnnotation;
import me.yushust.inject.cache.CacheAdapter;
import me.yushust.inject.cache.CacheAdapterBuilder;
import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.token.Token;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class CachedAnnotationTypeHandler implements AnnotationTypeHandler {

    private final CacheAdapter<Class<? extends Annotation>, Boolean> isLinkingAnnotation;

    public CachedAnnotationTypeHandler(CacheAdapterBuilder cacheBuilder) {
        this.isLinkingAnnotation = cacheBuilder.buildLoading(type ->
                type.isAnnotationPresent(LinkingAnnotation.class)
        );
    }

    @Override
    public <T> Key<T> keyOfField(Field field) {

        Token<T> fieldToken = new Token<>(field.getGenericType());

        for (Annotation annotation : field.getDeclaredAnnotations()) {
            if (isLinkingAnnotation(annotation)) {
                return Key.of(fieldToken, annotation);
            }
        }

        return Key.of(fieldToken);
    }

    @Override
    public boolean isLinkingAnnotation(Annotation annotation) {
        return isLinkingAnnotation.getOrLoad(annotation.annotationType());
    }

}
