package me.yushust.inject.internal;

import me.yushust.inject.Injector;
import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.type.TypeReference;

import java.lang.reflect.Member;
import java.util.Stack;

public interface InternalInjector extends Injector {

    <T> void injectMembers(TypeReference<T> typeReference, T instance, Stack<Member> injectionStack);

    default <T> T getInstance(Key<T> key, Stack<Member> injectionStack) {
        return getInstance(key, false, injectionStack);
    }

    <T> T getInstance(Key<T> key, boolean ignoreExplicitBindings, Stack<Member> injectionStack);

}
