package me.yushust.inject.bind;

import me.yushust.inject.Provider;
import me.yushust.inject.identity.Key;

public interface Binding<T> {

    Key<T> getKey();

    Provider<T> getProvider();

    void updateProvider(Provider<T> provider);

    boolean isProviderInjected();

    void setProviderInjected(boolean providerInjected);

}
