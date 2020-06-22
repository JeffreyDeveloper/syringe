package me.yushust.inject.internal.injector;

import me.yushust.inject.Injector;
import me.yushust.inject.exception.ExceptionFactory;
import me.yushust.inject.exception.UnsupportedInjectionException;
import me.yushust.inject.identity.token.Token;
import me.yushust.inject.internal.MembersInjector;
import me.yushust.inject.resolve.InjectableMember;
import me.yushust.inject.resolve.ResolvableKey;

import java.lang.reflect.Field;
import java.util.Set;

import static me.yushust.inject.internal.Preconditions.*;

public class FieldsInjector implements MembersInjector {

    private final Injector injector;
    private final Token<?> declaringClass;
    private final Set<InjectableMember> injections;

    public FieldsInjector(Injector injector, Token<?> declaringClass, Set<InjectableMember> injectableMembers) {
        this.injector = checkNotNull(injector);
        this.declaringClass = checkNotNull(declaringClass);
        this.injections = checkNotNull(injectableMembers);
    }

    @Override
    public void injectMembers(Object instance) throws UnsupportedInjectionException {

        checkNotNull(instance);
        checkState(
                instance.getClass() == declaringClass.getRawType(),
                "Provided value isn't compatible with specified declaring class"
        );

        for (InjectableMember injection : injections) {

            ResolvableKey<?> keyEntry = injection.getKeys().get(0);

            Field field = (Field) injection.getMember();
            Object injectedValue = injector.getInstance(keyEntry.getKey());

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
