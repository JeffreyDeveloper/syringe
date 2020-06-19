package me.yushust.inject.bind;

import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.token.Token;

public interface PrivateBinder extends Binder {

    <T> void expose(Class<T> key);

    <T> void expose(Token<T> key);

    <T> void expose(Key<T> key);

}
