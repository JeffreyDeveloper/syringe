package me.yushust.inject.scope;

import me.yushust.inject.Provider;
import me.yushust.inject.identity.Key;

public final class Scopes {

    public static final Scope SINGLETON = new SingletonScope();

    public static final Scope NONE = new Scope() {

        @Override
        public <T> Provider<T> wrap(Key<T> key, Provider<T> unscoped) {
            return unscoped;
        }

    };

    private Scopes() {
        throw new UnsupportedOperationException("This class couldn't be instantiated!");
    }

}
