package me.yushust.inject.internal.link.provider;

import me.yushust.inject.Provider;

import static me.yushust.inject.internal.Preconditions.checkNotNull;

public class InstanceLinkProvider<T> implements Provider<T> {

    private final T instance;

    public InstanceLinkProvider(T instance) {
        this.instance = checkNotNull(instance);
    }

    @Override
    public T get() {
        return instance;
    }

}
