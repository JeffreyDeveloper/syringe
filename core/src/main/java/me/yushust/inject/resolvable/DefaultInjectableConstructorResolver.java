package me.yushust.inject.resolvable;

import me.yushust.inject.Inject;
import me.yushust.inject.exception.NoInjectableConstructorException;
import me.yushust.inject.exception.UnsupportedInjectionException;
import me.yushust.inject.identity.token.Token;

import java.lang.reflect.Constructor;

public class DefaultInjectableConstructorResolver implements InjectableConstructorResolver {

    @Override
    @SuppressWarnings("unchecked")
    public <T> Constructor<T> findInjectableConstructor(Token<T> type) throws UnsupportedInjectionException,
            NoInjectableConstructorException {

        Class<?> rawType = type.getRawType();

        Constructor<?> injectableConstructor = null;

        for (Constructor<?> constructor : rawType.getDeclaredConstructors()) {

            Inject spec = constructor.getAnnotation(Inject.class);

            if (spec == null) {
                continue;
            }

            if (spec.optional()) {
                throw new UnsupportedInjectionException(
                        "Injection for constructors couldn't be optional! Class: " + rawType.getName()
                );
            }

            injectableConstructor = constructor;

        }

        if (injectableConstructor == null) {

            try {
                injectableConstructor = rawType.getDeclaredConstructor();
            } catch (NoSuchMethodException e) {
                throw new NoInjectableConstructorException("There're no usable constructors for class " + rawType.getName(), e);
            }
        }

        return (Constructor<T>) injectableConstructor;

    }

}
