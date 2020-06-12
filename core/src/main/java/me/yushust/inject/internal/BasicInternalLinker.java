package me.yushust.inject.internal;

import me.yushust.inject.link.Link;
import me.yushust.inject.link.LinkBuilder;
import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.token.Token;
import me.yushust.inject.internal.link.builder.SimpleLinkBuilder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BasicInternalLinker implements InternalLinker {

    protected final Map<Key<?>, Link<?>> links = new ConcurrentHashMap<>();

    @Override
    public <T> LinkBuilder.Qualified<T> link(Class<T> type) {
        return link(new Token<>(type));
    }

    @Override
    public <T> LinkBuilder.Qualified<T> link(Token<T> token) {
        return new SimpleLinkBuilder<>(this, token);
    }

    @Override
    public <T> LinkBuilder.Linkable<T> link(Key<T> key) {
        return new SimpleLinkBuilder<>(this, key);
    }

    @Override
    public <T> void removeLink(Token<T> token) {
        removeLink(Key.of(token));
    }

    @Override
    public <T> void removeLink(Key<T> key) {
        Preconditions.checkNotNull(key);
        links.remove(key);
    }

    @Override
    public <T> void setLink(Link<T> link) {
        Preconditions.checkNotNull(link);
        links.put(link.getKey(), link);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Link<T> findLink(Key<T> key) {
        Preconditions.checkNotNull(key);
        return (Link<T>) links.get(key);
    }

}
