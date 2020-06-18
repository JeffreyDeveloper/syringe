package me.yushust.inject.internal.injector;

public class DummyConstructorInjector<T> implements ConstructorInjector<T> {

    @Override
    public T createInstance(Object... extraParameters) {
        // yep, it's null
        return null;
    }

}
