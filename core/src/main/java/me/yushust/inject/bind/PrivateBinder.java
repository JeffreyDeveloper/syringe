package me.yushust.inject.bind;

import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.type.TypeReference;

public interface PrivateBinder extends Binder {

    <T> void expose(Class<T> key);

    <T> void expose(TypeReference<T> key);

    <T> void expose(Key<T> key);

}
