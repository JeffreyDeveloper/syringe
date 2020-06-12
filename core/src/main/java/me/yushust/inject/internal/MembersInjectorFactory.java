package me.yushust.inject.internal;

import me.yushust.inject.identity.token.Token;

public interface MembersInjectorFactory {

    MembersInjector getMembersInjector(Token<?> key);

}
