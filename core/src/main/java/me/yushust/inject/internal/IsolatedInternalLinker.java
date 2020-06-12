package me.yushust.inject.internal;

import me.yushust.inject.exception.InvalidLinkingException;
import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.token.Token;
import me.yushust.inject.link.IsolatedLinker;
import me.yushust.inject.link.Link;

public class IsolatedInternalLinker extends BasicInternalLinker implements IsolatedLinker, InternalLinker {

    private final InternalLinker parentLinker;

    public IsolatedInternalLinker(InternalLinker parentLinker) {
        this.parentLinker = Preconditions.checkNotNull(parentLinker);
    }

    @Override
    public <T> void expose(Class<T> key) {
        expose(new Token<>(key));
    }

    @Override
    public <T> void expose(Token<T> key) {
        expose(Key.of(key));
    }

    @Override
    public <T> void expose(Key<T> key) {
        Link<T> link = findLink(key);
        if (link == null) {
            throw new InvalidLinkingException("The exposing link isn't present!");
        }
        removeLink(key);
        parentLinker.setLink(link);
    }

    @Override
    public <T> Link<T> findLink(Key<T> key) {
        Link<T> link = super.findLink(key);
        if (link == null) {
            return parentLinker.findLink(key);
        }
        return link;
    }
}
