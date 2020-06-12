package me.yushust.inject;

import me.yushust.inject.identity.Key;

public interface Injector {

    void injectMembers(Object object);

    <T> T getInstance(Class<T> type);

    <T> T getInstance(Key<T> key);

}
