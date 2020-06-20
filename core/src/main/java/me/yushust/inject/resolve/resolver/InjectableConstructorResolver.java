package me.yushust.inject.resolve.resolver;

import java.lang.reflect.Constructor;

public interface InjectableConstructorResolver {

    <T> Constructor<T> findInjectableConstructor(Class<T> type);

}
