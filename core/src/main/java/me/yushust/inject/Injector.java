package me.yushust.inject;

import me.yushust.inject.identity.Key;
import me.yushust.inject.bind.Module;
import me.yushust.inject.identity.token.TypeReference;

import java.util.Arrays;

public interface Injector {

    void injectMembers(Object object);

    <T> void injectMembers(TypeReference<T> typeReference, T instance);

    <T> T getInstance(Class<T> type);

    <T> T getInstance(TypeReference<T> typeReference);

    <T> T getInstance(Key<T> key);

    <T> T getInstance(Key<T> key, boolean ignoreExplicitBindings);

    default Injector createChildInjector(Module... modules) {
        return createChildInjector(Arrays.asList(modules));
    };

    Injector createChildInjector(Iterable<Module> modules);

}
