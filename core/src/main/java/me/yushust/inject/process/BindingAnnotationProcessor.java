package me.yushust.inject.process;

import me.yushust.inject.internal.InternalBinder;

public interface BindingAnnotationProcessor {

    <T> boolean bind(InternalBinder binder, Class<T> clazz);

}
