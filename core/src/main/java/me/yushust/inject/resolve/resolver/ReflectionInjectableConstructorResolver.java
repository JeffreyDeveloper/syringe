package me.yushust.inject.resolve.resolver;

import me.yushust.inject.Inject;

import java.lang.reflect.Constructor;

public class ReflectionInjectableConstructorResolver implements InjectableConstructorResolver {

    @Override
    @SuppressWarnings("unchecked")
    public <T> Constructor<T> findInjectableConstructor(Class<T> type) { // null is a valid return valu

        Constructor<?> injectableConstructor = null;

        for (Constructor<?> constructor : type.getDeclaredConstructors()) {

            Inject spec = constructor.getAnnotation(Inject.class);
            boolean optional = false;

            if (spec == null) {
                if (constructor.getAnnotation(javax.inject.Inject.class) == null) {
                    continue;
                }
            } else {
                optional = spec.optional();
            }

            if (optional) { // ignore invalid constructors (constructors with @Inject(optional=true))
                continue;
            }

            injectableConstructor = constructor;

        }

        if (injectableConstructor == null) {
            try {
                injectableConstructor = type.getDeclaredConstructor();
            } catch (NoSuchMethodException e) {
                return null;
            }
        }

        return (Constructor<T>) injectableConstructor;

    }

}
