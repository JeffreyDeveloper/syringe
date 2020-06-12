package me.yushust.inject.resolvable;

import me.yushust.inject.exception.NoInjectableConstructorException;
import me.yushust.inject.exception.UnsupportedInjectionException;
import me.yushust.inject.identity.token.Token;

import java.lang.reflect.Constructor;

public interface InjectableConstructorResolver {

    <T> Constructor<T> findInjectableConstructor(Token<T> type) throws NoInjectableConstructorException,
            UnsupportedInjectionException;

}
