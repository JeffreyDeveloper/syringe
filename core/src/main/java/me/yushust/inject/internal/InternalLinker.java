package me.yushust.inject.internal;

import me.yushust.inject.link.Link;
import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.token.Token;
import me.yushust.inject.link.Linker;

public interface InternalLinker extends Linker {

    <T> void removeLink(Token<T> token);

    <T> void removeLink(Key<T> key);

    <T> void setLink(Link<T> link);

    <T> Link<T> findLink(Key<T> key);

}
