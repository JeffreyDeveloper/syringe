package team.unnamed.inject.process;

import team.unnamed.inject.internal.InternalBinder;

public interface ScopeAnnotationProcessor {

    <T> boolean scope(InternalBinder binder, Class<T> clazz);

}
