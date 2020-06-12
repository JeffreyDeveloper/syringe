package me.yushust.inject.internal.link.provider;

import me.yushust.inject.Provider;

import java.util.Objects;

public class InstanceLinkProvider<T> implements Provider<T> {

    private final T instance;

    public InstanceLinkProvider(T instance) {
        this.instance = Objects.requireNonNull(instance);
    }

    @Override
    public T get() {
        return instance;
    }

}
