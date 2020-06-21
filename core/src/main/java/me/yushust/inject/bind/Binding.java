package me.yushust.inject.bind;

import me.yushust.inject.Provider;
import me.yushust.inject.identity.Key;

public interface Binding<T> {

    Key<T> getKey();

    Provider<T> getProvider();

    boolean isProviderInjected();

    void setProviderInjected(boolean providerInjected);

}
