package team.unnamed.inject.internal.injector;

import team.unnamed.inject.exception.ExceptionFactory;
import team.unnamed.inject.exception.UnsupportedInjectionException;
import team.unnamed.inject.identity.type.TypeReference;
import team.unnamed.inject.internal.InternalInjector;
import team.unnamed.inject.internal.MembersInjector;
import team.unnamed.inject.resolve.InjectableMember;
import team.unnamed.inject.resolve.ResolvableKey;
import team.unnamed.inject.internal.Preconditions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class MethodsInjector implements MembersInjector {

    private final InternalInjector injector;
    private final TypeReference<?> declaringClass;
    private final Set<InjectableMember> injections;

    public MethodsInjector(InternalInjector injector, TypeReference<?> declaringClass, Set<InjectableMember> injections) {
        this.injector = Preconditions.checkNotNull(injector);
        this.declaringClass = Preconditions.checkNotNull(declaringClass);
        this.injections = Preconditions.checkNotNull(injections);
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
                    injectionStack.push(member);
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
