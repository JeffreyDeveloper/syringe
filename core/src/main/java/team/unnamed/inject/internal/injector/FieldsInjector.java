package team.unnamed.inject.internal.injector;

import team.unnamed.inject.exception.ExceptionFactory;
import team.unnamed.inject.exception.UnsupportedInjectionException;
import team.unnamed.inject.identity.type.TypeReference;
import team.unnamed.inject.internal.InternalInjector;
import team.unnamed.inject.internal.MembersInjector;
import team.unnamed.inject.resolve.InjectableMember;
import team.unnamed.inject.resolve.ResolvableKey;
import team.unnamed.inject.internal.Preconditions;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.util.Set;
import java.util.Stack;

public class FieldsInjector implements MembersInjector {

    private final InternalInjector injector;
    private final TypeReference<?> declaringClass;
    private final Set<InjectableMember> injections;

    public FieldsInjector(InternalInjector injector, TypeReference<?> declaringClass, Set<InjectableMember> injectableMembers) {
        this.injector = Preconditions.checkNotNull(injector);
        this.declaringClass = Preconditions.checkNotNull(declaringClass);
        this.injections = Preconditions.checkNotNull(injectableMembers);
    }

    @Override
    public void injectMembers(Object instance, Stack<Member> injectionStack) throws UnsupportedInjectionException {

        Preconditions.checkNotNull(instance);
        Preconditions.checkState(
                instance.getClass() == declaringClass.getRawType(),
                "Provided value isn't compatible with specified declaring class"
        );

        for (InjectableMember injection : injections) {

            ResolvableKey<?> keyEntry = injection.getKeys().get(0);

            Field field = (Field) injection.getMember();
            if (injectionStack.contains(field)) {
                injectionStack.push(field);
                throw ExceptionFactory.cyclicInjectionDetected(injectionStack);
            }

            injectionStack.push(field);
            Object injectedValue = injector.getInstance(keyEntry.getKey(), injectionStack);
            injectionStack.pop();


            if (injectedValue == null && !keyEntry.isOptional()) {
                throw ExceptionFactory.cannotInjectField(declaringClass, keyEntry.getKey(), field);
            }

            try {
                field.set(instance, injectedValue);
            } catch (IllegalAccessException e) {
                throw ExceptionFactory.cannotSetFieldValue(declaringClass, field, e);
            }

        }

    }

}
