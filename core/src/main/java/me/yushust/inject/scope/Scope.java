package me.yushust.inject.scope;

import me.yushust.inject.Provider;
import me.yushust.inject.identity.Key;

public interface Scope {

    <T> Provider<T> wrap(Key<T> key, Provider<T> unscoped);

}
