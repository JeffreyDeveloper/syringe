package me.yushust.inject;

import me.yushust.inject.identity.Key;
import me.yushust.inject.bind.Module;

import java.util.Arrays;

public interface Injector {

    void injectMembers(Object object);

    <T> T getInstance(Class<T> type);

    <T> T getInstance(Key<T> key);

    default Injector createChildInjector(Module... modules) {
        return createChildInjector(Arrays.asList(modules));
    };

    Injector createChildInjector(Iterable<Module> modules);

}
