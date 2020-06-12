package me.yushust.inject.resolvable;

import me.yushust.inject.identity.Key;

import java.util.List;

public interface InjectedConstructor<T> {

    List<Key<?>> getInjectedKeys();

    T newInstance(Object... extraArguments);

}
