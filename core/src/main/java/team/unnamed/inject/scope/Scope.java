package team.unnamed.inject.scope;

import team.unnamed.inject.Provider;
import team.unnamed.inject.identity.Key;

public interface Scope {

    <T> Provider<T> wrap(Key<T> key, Provider<T> unscoped);

}
