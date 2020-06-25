package me.yushust.inject.internal;

import me.yushust.inject.exception.UnsupportedInjectionException;

import java.lang.reflect.Member;
import java.util.Stack;

import static me.yushust.inject.internal.Preconditions.checkNotNull;

public class DelegatingMembersInjector implements MembersInjector {

    private final MembersInjector fieldsInjector;
    private final MembersInjector methodsInjector;

    public DelegatingMembersInjector(MembersInjector fieldsInjector, MembersInjector methodsInjector) {
        this.fieldsInjector = checkNotNull(fieldsInjector);
        this.methodsInjector = checkNotNull(methodsInjector);
    }

    @Override
    public void injectMembers(Object instance, Stack<Member> injections) throws UnsupportedInjectionException {
        fieldsInjector.injectMembers(instance, injections);
        methodsInjector.injectMembers(instance, injections);
    }

}
