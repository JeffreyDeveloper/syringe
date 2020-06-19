package me.yushust.inject.resolve;

import me.yushust.inject.identity.Key;

import static me.yushust.inject.internal.Preconditions.checkNotNull;

public class ResolvableKey<T> {

    private final Key<T> key;
    private final boolean optional;

    public ResolvableKey(Key<T> key, boolean optional) {
        checkNotNull(key);
        this.key = key;
        this.optional = optional;
    }

    public Key<T> getKey() {
        return key;
    }

    public boolean isOptional() {
        return optional;
    }

}
