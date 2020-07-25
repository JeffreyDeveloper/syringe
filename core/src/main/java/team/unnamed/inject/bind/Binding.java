package team.unnamed.inject.bind;

import team.unnamed.inject.Provider;
import team.unnamed.inject.identity.Key;

public interface Binding<T> {

    Key<T> getKey();

    Provider<T> getProvider();

    boolean isProviderInjected();

    void setProviderInjected(boolean providerInjected);

}
