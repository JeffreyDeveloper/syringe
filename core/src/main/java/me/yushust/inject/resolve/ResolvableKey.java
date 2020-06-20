package me.yushust.inject.resolve;

import me.yushust.inject.identity.Key;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResolvableKey<?> that = (ResolvableKey<?>) o;
        return optional == that.optional &&
                Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, optional);
    }
}
