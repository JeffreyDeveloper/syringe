package me.yushust.inject.resolvable;

import me.yushust.inject.identity.Key;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;

public interface AnnotationTypeHandler {

    <T> Key<T> keyOfField(Field field);

    boolean isLinkingAnnotation(Annotation annotation);

}
