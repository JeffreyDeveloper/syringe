package me.yushust.inject.internal.link.provider;

import me.yushust.inject.Inject;
import me.yushust.inject.Injector;
import me.yushust.inject.Provider;
import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.token.Token;

import static me.yushust.inject.internal.Preconditions.checkNotNull;

public class KeyLinkProvider<T> implements Provider<T> {

    @Inject private Injector injector;
    private final Key<T> key;

    public KeyLinkProvider(Token<T> token) {
        this(Key.of(token));
    }

    public KeyLinkProvider(Key<T> key) {
        this.key = checkNotNull(key);
    }

    @Override
    public T get() {
        return injector.getInstance(key);
    }

}
