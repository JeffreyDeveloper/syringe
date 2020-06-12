package me.yushust.inject.internal.injector;

import me.yushust.inject.Injector;
import me.yushust.inject.exception.NoInjectableConstructorException;
import me.yushust.inject.exception.UnsupportedInjectionException;
import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.token.Token;
import me.yushust.inject.resolvable.InjectableConstructorResolver;
import me.yushust.inject.resolvable.ParameterKeysResolver;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class ConstructorInjector<T> {

    private final Injector injector;

    private final Token<T> declaringClass;
    private final Constructor<T> constructor;
    private final List<Key<?>> parameterKeys;

    public ConstructorInjector(Token<T> declaringClass, Injector injector, InjectableConstructorResolver constructorResolver,
                               ParameterKeysResolver keysResolver)
            throws NoInjectableConstructorException, UnsupportedInjectionException {
        this.injector = injector;
        this.declaringClass = declaringClass;
        this.constructor = constructorResolver.findInjectableConstructor(declaringClass);
        this.parameterKeys = keysResolver.resolveParameterKeys(constructor);
    }

    public T createInstance(Object... extraParameters) {

        int parameterCount = parameterKeys.size() + extraParameters.length;

        Object[] parameters = new Object[parameterCount];

        for (int i = 0; i < parameterKeys.size(); i++) {

            Key<?> key = parameterKeys.get(i);
            Object param = injector.getInstance(key);

            if (param == null) {
                throw new IllegalStateException("Cannot instance parameter " + i);
            }

            parameters[i] = param;

        }

        if (parameterCount - parameterKeys.size() >= 0) {
            System.arraycopy(
                    extraParameters,
                    0,
                    parameters,
                    parameterKeys.size(),
                    extraParameters.length
            );
        }

        try {
            return constructor.newInstance(parameters);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to create a instance of class " + declaringClass.getRawType().getName(), e);
        }

    }

}
