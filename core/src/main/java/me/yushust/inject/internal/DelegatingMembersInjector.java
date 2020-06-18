package me.yushust.inject.internal;

import me.yushust.inject.exception.UnsupportedInjectionException;

import static me.yushust.inject.internal.Preconditions.checkNotNull;

public class DelegatingMembersInjector implements MembersInjector {

    private final MembersInjector fieldsInjector;
    private final MembersInjector methodsInjector;

    public DelegatingMembersInjector(MembersInjector fieldsInjector, MembersInjector methodsInjector) {
        this.fieldsInjector = checkNotNull(fieldsInjector);
        this.methodsInjector = checkNotNull(methodsInjector);
    }

    @Override
    public void injectMembers(Object instance) throws UnsupportedInjectionException {
        fieldsInjector.injectMembers(instance);
        methodsInjector.injectMembers(instance);
    }

}
