package team.unnamed.inject.internal.bind.builder;

import team.unnamed.inject.Provider;
import team.unnamed.inject.bind.Binding;
import team.unnamed.inject.bind.BindingBuilder;
import team.unnamed.inject.identity.Key;
import team.unnamed.inject.identity.type.TypeReference;
import me.yushust.inject.internal.*;
import team.unnamed.inject.internal.InternalBinder;
import team.unnamed.inject.internal.bind.InstanceBinding;
import team.unnamed.inject.internal.bind.LinkedBinding;
import team.unnamed.inject.internal.bind.ProviderKeyBinding;
import team.unnamed.inject.internal.bind.SimpleBinding;
import team.unnamed.inject.name.Names;
import team.unnamed.inject.scope.Scope;
import team.unnamed.inject.scope.Scopes;
import team.unnamed.inject.internal.Preconditions;

import java.lang.annotation.Annotation;

@SuppressWarnings("unchecked")
public class SimpleBindingBuilder<T> implements BindingBuilder.Qualified<T> {

    private final InternalBinder binder;

    private TypeReference<T> type;
    private Key<T> key;

    public SimpleBindingBuilder(InternalBinder binder, TypeReference<T> type) {
        this.binder = Preconditions.checkNotNull(binder);
        this.type = Preconditions.checkNotNull(type);
    }

    public SimpleBindingBuilder(InternalBinder binder, Key<T> key) {
        this.binder = Preconditions.checkNotNull(binder);
        this.key = Preconditions.checkNotNull(key);
    }

    @Override
    public Linkable<T> qualified(Class<? extends Annotation> qualifierType) {
        requireNullKey();
        key = Key.of(type, qualifierType, null);
        type = null;
        return this;
    }

    @Override
    public Linkable<T> qualified(Annotation qualifier) {
        requireNullKey();
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
        createKeyIfAbsent();
        binder.setBinding(new LinkedBinding<>(key, target));
        return this;
    }

    @Override
    public BindingBuilder<T> toProvider(Provider<? extends T> provider) {
        createKeyIfAbsent();
        binder.setBinding(new SimpleBinding<>(key, (Provider<T>) provider));
        return this;
    }

    @Override
    public BindingBuilder<T> toProvider(Class<? extends Provider<? extends T>> provider) {
        createKeyIfAbsent();
        binder.setBinding(new ProviderKeyBinding<>(key, provider));
        return this;
    }

    @Override
    public void toInstance(T instance) {
        createKeyIfAbsent();
        binder.setBinding(new InstanceBinding<>(key, instance));
    }

    @Override
    public void scope(Scope scope) {
        createKeyIfAbsent();
        Binding<T> binding = binder.findBinding(key);

        if (binding == null) {
            binding = new LinkedBinding<>(key, key); // refers to itself
        }

        binder.removeBinding(key);
        Provider<T> provider = binding.getProvider();
        provider = scope.wrap(key, provider);
        binder.setBinding(new SimpleBinding<>(key, provider));
    }

    @Override
    public void singleton() {
        scope(Scopes.SINGLETON); // just a method alias for "scope(Scopes.SINGLETON)"
    }

    private void createKeyIfAbsent() {
        if (key == null && type != null) {
            key = Key.of(type);
            type = null;
        }
        Preconditions.checkState(key != null, "Couldn't create a key. There's not a type!");
    }

    private void requireNullKey() {
        Preconditions.checkState(key == null, "The key is already created!");
        Preconditions.checkState(type != null, "Couldn't create a key without a base type!");
    }

}
