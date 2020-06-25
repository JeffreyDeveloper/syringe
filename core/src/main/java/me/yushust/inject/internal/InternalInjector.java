package me.yushust.inject.internal;

import me.yushust.inject.Injector;
import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.token.Token;

import java.lang.reflect.Member;
import java.util.Stack;

public interface InternalInjector extends Injector {

    <T> void injectMembers(Token<T> token, T instance, Stack<Member> injectionStack);

    default <T> T getInstance(Key<T> key, Stack<Member> injectionStack) {
        return getInstance(key, false, injectionStack);
    }

    <T> T getInstance(Key<T> key, boolean ignoreExplicitBindings, Stack<Member> injectionStack);

}
