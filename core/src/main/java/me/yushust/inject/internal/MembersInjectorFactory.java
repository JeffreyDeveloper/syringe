package me.yushust.inject.internal;

import me.yushust.inject.Injector;
import me.yushust.inject.identity.token.Token;
import me.yushust.inject.internal.injector.ConstructorInjector;

public interface MembersInjectorFactory {

    MembersInjector getMembersInjector(Token<?> key);

    <T> ConstructorInjector<T> getConstructorInjector(Token<T> key);

    MembersInjectorFactory usingInjector(InternalInjector injector);

}
