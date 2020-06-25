package me.yushust.inject.internal.injector;

import me.yushust.inject.exception.ExceptionFactory;
import me.yushust.inject.exception.UnsupportedInjectionException;
import me.yushust.inject.identity.token.Token;
import me.yushust.inject.internal.InternalInjector;
import me.yushust.inject.internal.MembersInjector;
import me.yushust.inject.resolve.InjectableMember;
import me.yushust.inject.resolve.ResolvableKey;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import static me.yushust.inject.internal.Preconditions.checkNotNull;

public class MethodsInjector implements MembersInjector {

    private final InternalInjector injector;
    private final Token<?> declaringClass;
    private final Set<InjectableMember> injections;

    public MethodsInjector(InternalInjector injector, Token<?> declaringClass, Set<InjectableMember> injections) {
        this.injector = checkNotNull(injector);
        this.declaringClass = checkNotNull(declaringClass);
        this.injections = checkNotNull(injections);
    }

    @Override
    public void injectMembers(Object instance, Stack<Member> injectionStack) throws UnsupportedInjectionException {

        for (InjectableMember injection : injections) {

            List<ResolvableKey<?>> parameterKeys = injection.getKeys();
            Member member = injection.getMember();

            if (!(member instanceof Method)) {
                continue;
            }

            Method method = (Method) member;

            Object[] parameters = new Object[parameterKeys.size()];

            for (int i = 0; i < parameterKeys.size(); i++) {

                ResolvableKey<?> parameterKey = parameterKeys.get(i);

                if (injectionStack.contains(member)) {
                    throw ExceptionFactory.cyclicInjectionDetected(injectionStack);
                }

                injectionStack.push(member);
                Object injectedValue = injector.getInstance(parameterKey.getKey(), injectionStack);
                injectionStack.pop();

                if (injectedValue == null && !parameterKey.isOptional()) {
                    throw ExceptionFactory.cannotInjectMethod(declaringClass, parameterKey.getKey(), method);
                }

                parameters[i] = injectedValue;
            }

            try {
                method.invoke(instance, parameters);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw ExceptionFactory.cannotInvokeInjectableMethod(declaringClass, method, e);
            }

        }

    }

}
