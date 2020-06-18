package me.yushust.inject.internal.injector;

import me.yushust.inject.Injector;
import me.yushust.inject.exception.UnsupportedInjectionException;
import me.yushust.inject.identity.token.Token;
import me.yushust.inject.internal.MembersInjector;
import me.yushust.inject.resolve.InjectableMember;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
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

            InjectableMember.KeyEntry<?> keyEntry = injection.getKeys().get(0);

            Member member = injection.getMember();

            if (!(member instanceof Field)) {
                continue;
            }

            Field field = (Field) member;
            Object injectedValue = injector.getInstance(keyEntry.getKey());

            if (injectedValue == null && !keyEntry.isOptional()) {
                throw new UnsupportedInjectionException(
                        "Cannot create an instance for key " + keyEntry.toString() + " of class " + declaringClass.toString()
                );
            }

            try {
                field.set(instance, injectedValue);
            } catch (IllegalAccessException e) {
                throw new UnsupportedInjectionException(
                        "Cannot access to field " + field.getName() + " of class " + declaringClass.toString(), e
                );
            }

        }

    }

}
