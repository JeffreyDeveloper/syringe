package me.yushust.inject.internal;

import me.yushust.inject.bind.Binding;
import me.yushust.inject.bind.BindingBuilder;
import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.token.Token;
import me.yushust.inject.internal.bind.builder.SimpleBindingBuilder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static me.yushust.inject.internal.Preconditions.checkNotNull;

public class SimpleBinder implements InternalBinder {

    private final Map<Key<?>, Binding<?>> bindingsMap = new ConcurrentHashMap<>();

    @Override
    public <T> BindingBuilder.Qualified<T> bind(Class<T> type) {
        checkNotNull(type);
        return bind(new Token<>(type));
    }

    @Override
    public <T> BindingBuilder.Qualified<T> bind(Token<T> token) {
        checkNotNull(token);
        return new SimpleBindingBuilder<T>(this, token);
    }

    @Override
    public <T> BindingBuilder.Linkable<T> bind(Key<T> key) {
        checkNotNull(key);
        return new SimpleBindingBuilder<T>(this, key);
    }

    @Override
    public <T> void removeBinding(Key<T> key) {
        checkNotNull(key);
        bindingsMap.remove(key);
    }

    @Override
    public <T> void setBinding(Binding<T> binding) {
        checkNotNull(binding);
        bindingsMap.put(binding.getKey(), binding);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Binding<T> findBinding(Key<T> key) {
        checkNotNull(key);
        return (Binding<T>) bindingsMap.get(key);
    }

}
