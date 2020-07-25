package team.unnamed.inject.internal.bind;

import team.unnamed.inject.Provider;
import team.unnamed.inject.bind.Binding;
import team.unnamed.inject.identity.Key;
import team.unnamed.inject.internal.Preconditions;

public class SimpleBinding<T> implements Binding<T> {

    private final Key<T> key;
    private final Provider<T> provider;
    private boolean providerInjected;

    public SimpleBinding(Key<T> key, Provider<T> provider) {
        this.key = Preconditions.checkNotNull(key);
        this.provider = Preconditions.checkNotNull(provider);
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
    public boolean isProviderInjected() {
        return providerInjected;
    }

    @Override
    public void setProviderInjected(boolean providerInjected) {
        this.providerInjected = providerInjected;
    }

    @Override
    public String toString() {
        return "Binding{" +
                "key=" + key +
                ", provider=" + provider +
                '}';
    }
}
