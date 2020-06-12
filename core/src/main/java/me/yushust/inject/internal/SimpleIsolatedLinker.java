package me.yushust.inject.internal;

import me.yushust.inject.exception.InvalidLinkingException;
import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.token.Token;
import me.yushust.inject.link.IsolatedLinker;
import me.yushust.inject.link.Link;

import java.util.Objects;

public class SimpleIsolatedLinker extends LinkerImpl implements IsolatedLinker {

    private final InternalLinker parentLinker;

    private SimpleIsolatedLinker(IsolatedInternalLinker isolatedInternalLinker, InternalLinker parentLinker) {
        super(isolatedInternalLinker);
        this.parentLinker = parentLinker;
    }

    @Override
    public <T> void expose(Class<T> key) {
        expose(new Token<>(key));
    }

    @Override
    public <T> void expose(Token<T> key) {
        expose(new Key<>(key, null, null));
    }

    @Override
    public <T> void expose(Key<T> key) {
        Link<T> link = linker.findLink(key);
        if (link == null) {
            throw new InvalidLinkingException("The exposing link isn't present!");
        }
        linker.removeLink(key);
        parentLinker.setLink(link);
    }

    public static SimpleIsolatedLinker create(InternalLinker parentLinker) {
        IsolatedInternalLinker isolatedInternalLinker = new IsolatedInternalLinker(parentLinker);
        return new SimpleIsolatedLinker(isolatedInternalLinker, parentLinker);
    }

    protected static class IsolatedInternalLinker extends InternalLinkerImpl implements InternalLinker {

        private final InternalLinker parentLinker;

        public IsolatedInternalLinker(InternalLinker parentLinker) {
            this.parentLinker = Objects.requireNonNull(parentLinker);
        }

        @Override
        public <T> Link<T> findLink(Key<T> key) {
            Link<T> found = super.findLink(key);
            if (found == null) {
                return parentLinker.findLink(key);
            }
            return found;
        }

    }

}
