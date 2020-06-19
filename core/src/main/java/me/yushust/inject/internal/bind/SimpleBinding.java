package me.yushust.inject.internal.bind;

import me.yushust.inject.Provider;
import me.yushust.inject.bind.Binding;
import me.yushust.inject.identity.Key;

import static me.yushust.inject.internal.Preconditions.checkNotNull;

public class SimpleBinding<T> implements Binding<T> {

    private final Key<T> key;
    private Provider<T> provider;
    private boolean providerInjected;

    public SimpleBinding(Key<T> key, Provider<T> provider) {
        this.key = checkNotNull(key);
        this.provider = checkNotNull(provider);
    }

    @Override
    public Key<T> getKey() {
        return key;
    }

    @Override
    public Provider<T> getProvider() {
        return provider;
    }

    @Override
    public void updateProvider(Provider<T> provider) {
        this.provider = checkNotNull(provider);
    }

    @Override
    public boolean isProviderInjected() {
        return providerInjected;
    }

    @Override
    public void setProviderInjected(boolean providerInjected) {
        this.providerInjected = providerInjected;
    }

}
