package me.yushust.inject.process;

import me.yushust.inject.internal.InternalBinder;

public interface ScopeAnnotationProcessor {

    <T> boolean scope(InternalBinder binder, Class<T> clazz);

}
