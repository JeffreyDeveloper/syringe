package me.yushust.inject.internal;

import me.yushust.inject.link.Linker;
import me.yushust.inject.link.Link;
import me.yushust.inject.link.LinkBuilder;
import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.token.Token;
import me.yushust.inject.internal.link.builder.SimpleLinkBuilder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LinkerImpl implements Linker {

    private final InternalLinker linker = new InternalLinkerImpl();

    @Override
    public <T> LinkBuilder.Qualified<T> link(Class<T> type) {
        return link(new Token<>(type));
    }

    @Override
    public <T> LinkBuilder.Qualified<T> link(Token<T> token) {
        return new SimpleLinkBuilder<>(linker, token);
    }

    @Override
    public <T> LinkBuilder.Linkable<T> link(Key<T> key) {
        return new SimpleLinkBuilder<>(linker, key);
    }

    public InternalLinker getInternalLinker() {
        return linker;
    }

    private static class InternalLinkerImpl implements InternalLinker {

        private final Map<Key<?>, Link<?>> links = new ConcurrentHashMap<>();

        @Override
        public <T> void removeLink(Token<T> token) {
            removeLink(new Key<>(token, null, null));
        }

        @Override
        public <T> void removeLink(Key<T> key) {
            links.remove(key);
        }

        @Override
        public <T> void setLink(Link<T> link) {
            links.put(link.getKey(), link);
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> Link<T> findLink(Key<T> key) {
            return (Link<T>) links.get(key);
        }

    }

}
