package me.yushust.inject.link;

import me.yushust.inject.Provider;
import me.yushust.inject.identity.Key;

public interface Link<T> {

    Key<T> getKey();

    Provider<T> getProvider();

    void updateProvider(Provider<T> provider);

    boolean isProviderInjected();

    void setProviderInjected(boolean providerInjected);

}
