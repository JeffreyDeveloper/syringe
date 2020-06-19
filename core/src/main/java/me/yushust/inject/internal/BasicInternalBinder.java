package me.yushust.inject.internal;

import me.yushust.inject.bind.Binding;
import me.yushust.inject.bind.BindingBuilder;
import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.token.Token;
import me.yushust.inject.internal.bind.builder.SimpleBindingBuilder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BasicInternalBinder implements InternalBinder {

    protected final Map<Key<?>, Binding<?>> links = new ConcurrentHashMap<>();

    @Override
    public <T> BindingBuilder.Qualified<T> bind(Class<T> type) {
        return bind(new Token<>(type));
    }

    @Override
    public <T> BindingBuilder.Qualified<T> bind(Token<T> token) {
        return new SimpleBindingBuilder<>(this, token);
    }

    @Override
    public <T> BindingBuilder.Linkable<T> bind(Key<T> key) {
        return new SimpleBindingBuilder<>(this, key);
    }

    @Override
    public <T> void removeBinding(Token<T> token) {
        removeBinding(Key.of(token));
    }

    @Override
    public <T> void removeBinding(Key<T> key) {
        Preconditions.checkNotNull(key);
        links.remove(key);
    }

    @Override
    public <T> void setBinding(Binding<T> binding) {
        Preconditions.checkNotNull(binding);
        links.put(binding.getKey(), binding);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Binding<T> findBinding(Key<T> key) {
        Preconditions.checkNotNull(key);
        return (Binding<T>) links.get(key);
    }

}
