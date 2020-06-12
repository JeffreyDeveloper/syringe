package me.yushust.inject.internal.link;

import me.yushust.inject.Provider;
import me.yushust.inject.link.Link;
import me.yushust.inject.identity.Key;

import java.util.Objects;

public class SimpleLink<T> implements Link<T> {

    private final Key<T> key;
    private Provider<T> provider;
    private boolean providerInjected;

    public SimpleLink(Key<T> key, Provider<T> provider) {
        this.key = Objects.requireNonNull(key);
        this.provider = Objects.requireNonNull(provider);
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
        this.provider = provider;
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
