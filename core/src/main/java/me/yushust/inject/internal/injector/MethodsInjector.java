package me.yushust.inject.internal.injector;

import me.yushust.inject.Injector;
import me.yushust.inject.exception.UnsupportedInjectionException;
import me.yushust.inject.identity.token.Token;
import me.yushust.inject.internal.MembersInjector;
import me.yushust.inject.resolve.InjectableMember;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import static me.yushust.inject.internal.Preconditions.checkNotNull;

public class MethodsInjector implements MembersInjector {

    private final Injector injector;
    private final Token<?> declaringClass;
    private final Set<InjectableMember> injections;

    public MethodsInjector(Injector injector, Token<?> declaringClass, Set<InjectableMember> injections) {
        this.injector = checkNotNull(injector);
        this.declaringClass = checkNotNull(declaringClass);
        this.injections = checkNotNull(injections);
    }

    @Override
    public void injectMembers(Object instance) throws UnsupportedInjectionException {

        for (InjectableMember injection : injections) {

            List<InjectableMember.KeyEntry<?>> parameterKeys = injection.getKeys();
            Member member = injection.getMember();

            if (!(member instanceof Method)) {
                continue;
            }

            Method method = (Method) member;

            Object[] parameters = new Object[parameterKeys.size()];

            for (int i = 0; i < parameterKeys.size(); i++) {

                InjectableMember.KeyEntry<?> parameterKey = parameterKeys.get(i);
                Object injectedValue = injector.getInstance(parameterKey.getKey());

                if (injectedValue == null && !parameterKey.isOptional()) {
                    throw new UnsupportedInjectionException(
                        "Cannot create an instance for key " + parameterKey.toString() + " for method " + method.getName() +
                                " of class " + declaringClass.toString()
                    );
                }

                parameters[i] = injectedValue;
            }

            try {
                method.invoke(instance, parameters);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new UnsupportedInjectionException(
                        "Cannot invoke method " + method.getName() + " of class " + declaringClass.toString(), e
                );
            }

        }

    }

}
