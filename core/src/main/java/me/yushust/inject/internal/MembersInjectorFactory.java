package me.yushust.inject.internal;

import me.yushust.inject.internal.injector.ConstructorInjector;

public interface MembersInjectorFactory {

    MembersInjector getMembersInjector(Class<?> key);

    <T> ConstructorInjector<T> getConstructorInjector(Class<T> key);

}
