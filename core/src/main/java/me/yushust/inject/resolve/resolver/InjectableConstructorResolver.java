package me.yushust.inject.resolve.resolver;

import me.yushust.inject.identity.type.TypeReference;

import java.lang.reflect.Constructor;

public interface InjectableConstructorResolver {

    <T> Constructor<T> findInjectableConstructor(TypeReference<T> type);

}
