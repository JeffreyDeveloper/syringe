package me.yushust.inject.internal;

import me.yushust.inject.exception.UnsupportedInjectionException;

public interface MembersInjector {

    void injectMembers(Object instance) throws UnsupportedInjectionException;

}
