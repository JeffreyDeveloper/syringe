package me.yushust.inject.internal.link.provider;

import me.yushust.inject.Inject;
import me.yushust.inject.Injector;
import me.yushust.inject.Provider;
import me.yushust.inject.identity.Key;

import java.util.Objects;

public class KeyLinkProvider<T> implements Provider<T> {

    @Inject private Injector injector;
    private final Key<T> key;

    public KeyLinkProvider(Key<T> key) {
        this.key = Objects.requireNonNull(key);
    }

    @Override
    public T get() {
        return injector.getInstance(key);
    }

}
