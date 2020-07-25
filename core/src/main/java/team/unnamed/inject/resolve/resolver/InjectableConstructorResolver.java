package team.unnamed.inject.resolve.resolver;

import team.unnamed.inject.identity.type.TypeReference;

import java.lang.reflect.Constructor;

public interface InjectableConstructorResolver {

    <T> Constructor<T> findInjectableConstructor(TypeReference<T> type);

}
