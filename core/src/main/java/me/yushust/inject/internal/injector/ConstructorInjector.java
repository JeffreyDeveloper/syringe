package me.yushust.inject.internal.injector;

public interface ConstructorInjector<T> {

    T createInstance(Object... extraParameters);

}
