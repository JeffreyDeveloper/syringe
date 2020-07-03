package me.yushust.inject.internal;

import me.yushust.inject.identity.token.TypeReference;
import me.yushust.inject.internal.injector.ConstructorInjector;

public interface MembersInjectorFactory {

    MembersInjector getMembersInjector(TypeReference<?> key);

    <T> ConstructorInjector<T> getConstructorInjector(TypeReference<T> key);

    MembersInjectorFactory usingInjector(InternalInjector injector);

}
