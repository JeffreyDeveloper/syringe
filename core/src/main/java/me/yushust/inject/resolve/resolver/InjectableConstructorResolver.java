package me.yushust.inject.resolve.resolver;

import me.yushust.inject.identity.token.Token;

import java.lang.reflect.Constructor;

public interface InjectableConstructorResolver {

    <T> Constructor<T> findInjectableConstructor(Token<T> type);

}
