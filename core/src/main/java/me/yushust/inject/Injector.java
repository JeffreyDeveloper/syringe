package me.yushust.inject;

import me.yushust.inject.bind.Binding;
import me.yushust.inject.identity.Key;
import me.yushust.inject.bind.Module;

import java.util.Arrays;
import java.util.Collection;

public interface Injector {

    Collection<Binding<?>> getBindings();

    void injectMembers(Object object);

    <T> T getInstance(Class<T> type);

    <T> T getInstance(Key<T> key);

    <T> T getInstance(Key<T> key, boolean ignoreExplicitBindings);

    default Injector createChildInjector(Module... modules) {
        return createChildInjector(Arrays.asList(modules));
    };

    Injector createChildInjector(Iterable<Module> modules);

}
