package me.yushust.inject.internal;

import me.yushust.inject.exception.UnsupportedInjectionException;

import java.util.Objects;

public class DelegatingMembersInjector implements MembersInjector {

    private final MembersInjector fieldsInjector;
    private final MembersInjector methodsInjector;

    public DelegatingMembersInjector(MembersInjector fieldsInjector, MembersInjector methodsInjector) {
        this.fieldsInjector = Objects.requireNonNull(fieldsInjector);
        this.methodsInjector = Objects.requireNonNull(methodsInjector);
    }

    @Override
    public void injectMembers(Object instance) throws UnsupportedInjectionException {
        fieldsInjector.injectMembers(instance);
        methodsInjector.injectMembers(instance);
    }

}
