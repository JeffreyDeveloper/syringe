package me.yushust.inject.link;

import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.token.Token;

public interface IsolatedLinker extends Linker {

    <T> void expose(Class<T> key);

    <T> void expose(Token<T> key);

    <T> void expose(Key<T> key);

}
