package team.unnamed.inject.process;

import team.unnamed.inject.internal.InternalBinder;

public interface BindingAnnotationProcessor {

    <T> boolean bind(InternalBinder binder, Class<T> clazz);

}
