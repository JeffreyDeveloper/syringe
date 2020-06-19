package me.yushust.inject.internal.bind.builder;

import me.yushust.inject.Provider;
import me.yushust.inject.bind.Binding;
import me.yushust.inject.bind.BindingBuilder;
import me.yushust.inject.exception.InvalidBindingException;
import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.token.Token;
import me.yushust.inject.internal.*;
import me.yushust.inject.internal.bind.InstanceBinding;
import me.yushust.inject.internal.bind.LinkedBinding;
import me.yushust.inject.internal.bind.ProviderKeyBinding;
import me.yushust.inject.internal.bind.SimpleBinding;
import me.yushust.inject.name.Names;
import me.yushust.inject.scope.Scope;

import java.lang.annotation.Annotation;

import static me.yushust.inject.internal.Preconditions.checkNotNull;

@SuppressWarnings("unchecked")
public class SimpleBindingBuilder<T> implements BindingBuilder.Qualified<T> {

    private final InternalBinder binder;

    private Token<T> type;
    private Key<T> key;

    public SimpleBindingBuilder(InternalBinder binder, Token<T> type) {
        this.binder = checkNotNull(binder);
        this.type = checkNotNull(type);
    }

    public SimpleBindingBuilder(InternalBinder binder, Key<T> key) {
        this.binder = checkNotNull(binder);
        this.key = checkNotNull(key);
    }

    @Override
    public Linkable<T> qualified(Class<? extends Annotation> qualifierType) {
        checkAnnotationState();
        key = Key.of(type, qualifierType, null);
        type = null;
        return this;
    }

    @Override
    public Linkable<T> qualified(Annotation qualifier) {
        checkAnnotationState();
        key = Key.of(type, qualifier);
        type = null;
        return this;
    }

    @Override
    public Linkable<T> named(String name) {
        return qualified(Names.named(name));
    }

    @Override
    public BindingBuilder<T> to(Class<? extends T> target) {
        return to(Key.of(target));
    }

    @Override
    public BindingBuilder<T> to(Key<? extends T> target) {
        checkTargetingState();
        binder.setBinding(new LinkedBinding<>(key, target));
        return this;
    }

    @Override
    public BindingBuilder<T> toProvider(Provider<? extends T> provider) {
        checkTargetingState();
        binder.setBinding(new SimpleBinding<>(key, (Provider<T>) provider));
        return this;
    }

    @Override
    public BindingBuilder<T> toProvider(Class<? extends Provider<? extends T>> provider) {
        checkTargetingState();
        binder.setBinding(new ProviderKeyBinding<>(key, provider));
        return this;
    }

    @Override
    public void toInstance(T instance) {
        checkTargetingState();
        binder.setBinding(new InstanceBinding<>(key, instance));
    }

    @Override
    public void scope(Scope scope) {
        checkTargetingState();
        Binding<T> binding = binder.findBinding(key);

        if (binding == null) {
            binding = new LinkedBinding<>(key, key); // refers to itself
        }

        binder.removeBinding(key);
        Provider<T> provider = binding.getProvider();
        provider = scope.wrap(key, provider);
        binder.setBinding(new SimpleBinding<>(key, provider));
    }

    private void checkTargetingState() {
        if (type != null) {
            key = Key.of(type);
        }
        if (key == null) {
            throw new InvalidBindingException("There's no a key!");
        }
    }

    private void checkAnnotationState() {
        if (key != null) {
            throw new InvalidBindingException("The key already have an annotation strategy!");
        }
        if (type == null) {
            throw new InvalidBindingException("Cannot add qualifier if the type isn't defined yet!");
        }
    }

}
