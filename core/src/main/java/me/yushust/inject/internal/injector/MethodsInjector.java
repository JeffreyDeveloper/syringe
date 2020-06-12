package me.yushust.inject.internal.injector;

import me.yushust.inject.Inject;
import me.yushust.inject.Injector;
import me.yushust.inject.exception.UnsupportedInjectionException;
import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.token.Token;
import me.yushust.inject.internal.MembersInjector;
import me.yushust.inject.resolvable.ParameterKeysResolver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MethodsInjector implements MembersInjector {

    private final ParameterKeysResolver keysResolver;
    private final Injector injector;

    private final Token<?> declaringClass;
    private final List<InjectableMethod> injections;

    public MethodsInjector(ParameterKeysResolver keysResolver, Injector injector, Token<?> declaringClass) {
        this.keysResolver = Objects.requireNonNull(keysResolver);
        this.injector = Objects.requireNonNull(injector);
        this.declaringClass = Objects.requireNonNull(declaringClass);
        this.injections = this.getInjections();
    }

    @Override
    public void injectMembers(Object instance) throws UnsupportedInjectionException {

        for (InjectableMethod injection : injections) {

            Method method = injection.method;
            List<Key<?>> keys = injection.parameterKeys;
            Object[] parameters = new Object[keys.size()];

            for (int i = 0; i < keys.size(); i++) {
                Key<?> key = keys.get(i);
                Object injectedValue = injector.getInstance(key);

                if (injectedValue == null) {
                    throw new UnsupportedInjectionException(
                            "Cannot create an instance for key " + key.toString() + " for method " + method.getName() +
                                    " of class " + declaringClass.toString()
                    );
                }

                parameters[i] = injectedValue;
            }

            try {
                method.invoke(instance, parameters);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new UnsupportedInjectionException(
                        "Cannot invoke method " + method.getName() + " of class " + declaringClass.toString()
                );
            }

        }

    }

    private List<InjectableMethod> getInjections() {

        List<InjectableMethod> injections = new ArrayList<>();

        for (Method method : declaringClass.getRawType().getDeclaredMethods()) {

            Inject spec = method.getAnnotation(Inject.class);

            if (spec == null) {
                continue;
            }

            if (spec.optional()) {
                throw new IllegalStateException("optional cannot be true for methods! (Method " + method.getName() + ")");
            }

            injections.add(new InjectableMethod(keysResolver.resolveParameterKeys(method), method));

        }

        return injections;

    }

    public static class InjectableMethod {

        private final List<Key<?>> parameterKeys;
        private final Method method;

        public InjectableMethod(List<Key<?>> parameterKeys, Method method) {
            this.parameterKeys = parameterKeys;
            this.method = method;
        }

    }

}
