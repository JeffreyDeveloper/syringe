package me.yushust.inject.internal.link.builder;

import me.yushust.inject.Provider;
import me.yushust.inject.link.Link;
import me.yushust.inject.link.LinkBuilder;
import me.yushust.inject.exception.InvalidLinkingException;
import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.token.Token;
import me.yushust.inject.internal.*;
import me.yushust.inject.internal.link.SimpleLink;
import me.yushust.inject.internal.link.provider.ClassLinkProvider;
import me.yushust.inject.internal.link.provider.InstanceLinkProvider;
import me.yushust.inject.internal.link.provider.KeyLinkProvider;
import me.yushust.inject.name.Names;
import me.yushust.inject.scope.Scope;

import java.lang.annotation.Annotation;
import java.util.Objects;

@SuppressWarnings("unchecked")
public class SimpleLinkBuilder<T> implements LinkBuilder.Qualified<T> {

    private final InternalLinker linker;

    private Token<T> type;
    private Key<T> key;

    public SimpleLinkBuilder(InternalLinker linker, Token<T> type) {
        this.linker = Objects.requireNonNull(linker);
        this.type = Objects.requireNonNull(type);
    }

    public SimpleLinkBuilder(InternalLinker linker, Key<T> key) {
        this.linker = Objects.requireNonNull(linker);
        this.key = Objects.requireNonNull(key);
    }

    @Override
    public Linkable<T> qualified(Class<? extends Annotation> qualifierType) {
        checkAnnotationState();
        key = new Key<>(type, qualifierType, null);
        type = null;
        return this;
    }

    @Override
    public Linkable<T> qualified(Annotation qualifier) {
        checkAnnotationState();
        key = new Key<>(type, qualifier.annotationType(), qualifier);
        type = null;
        return this;
    }

    @Override
    public Linkable<T> named(String name) {
        return qualified(Names.named(name));
    }

    @Override
    public LinkBuilder<T> to(Class<? extends T> target) {
        checkTargetingState();
        linker.setLink(new SimpleLink<>(key, new ClassLinkProvider<>((Class<T>) target)));
        return this;
    }

    @Override
    public LinkBuilder<T> to(Key<? extends T> target) {
        checkTargetingState();
        linker.setLink(new SimpleLink<>(key, new KeyLinkProvider<>((Key<T>) target)));
        return this;
    }

    @Override
    public LinkBuilder<T> toProvider(Provider<? extends T> provider) {
        checkTargetingState();
        linker.setLink(new SimpleLink<>(key, (Provider<T>) provider));
        return this;
    }

    @Override
    public void toInstance(T instance) {
        checkTargetingState();
        linker.setLink(new SimpleLink<>(key, new InstanceLinkProvider<>(instance)));
    }

    @Override
    public void scope(Scope scope) {
        checkTargetingState();
        Link<T> link = linker.findLink(key);

        if (link == null) {
            throw new InvalidLinkingException("There's no a saved link!");
        }

        linker.removeLink(key);
        Provider<T> provider = link.getProvider();
        provider = scope.wrap(key, provider);
        linker.setLink(new SimpleLink<>(key, provider));
    }

    private void checkTargetingState() {
        if (type != null) {
            key = new Key<>(type, null, null);
        }
        if (key == null) {
            throw new InvalidLinkingException("There's no a key!");
        }
    }

    private void checkAnnotationState() {
        if (key != null) {
            throw new InvalidLinkingException("The key already have an annotation strategy!");
        }
        if (type == null) {
            throw new InvalidLinkingException("Cannot add qualifier if the type isn't defined yet!");
        }
    }

}
