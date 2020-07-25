package team.unnamed.inject.internal.injector;

public interface ConstructorInjector<T> {

    T createInstance(Object... extraParameters);

}
