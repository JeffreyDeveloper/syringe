package team.unnamed.inject.internal;

import team.unnamed.inject.bind.Binding;
import team.unnamed.inject.bind.BindingBuilder;
import team.unnamed.inject.identity.Key;
import team.unnamed.inject.identity.type.TypeReference;
import team.unnamed.inject.internal.bind.builder.SimpleBindingBuilder;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static team.unnamed.inject.internal.Preconditions.checkNotNull;

public class SimpleBinder implements InternalBinder {

    private final Map<Key<?>, Binding<?>> bindingsMap = new ConcurrentHashMap<>();

    @Override
    public <T> BindingBuilder.Qualified<T> bind(Class<T> type) {
        checkNotNull(type);
        return bind(TypeReference.of(type));
    }

    @Override
    public <T> BindingBuilder.Qualified<T> bind(TypeReference<T> typeReference) {
        checkNotNull(typeReference);
        return new SimpleBindingBuilder<>(this, typeReference);
    }

    @Override
    public <T> BindingBuilder.Linkable<T> bind(Key<T> key) {
        checkNotNull(key);
        return new SimpleBindingBuilder<>(this, key);
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

    @Override
    public Collection<Binding<?>> getBindings() {
        return Collections.unmodifiableCollection(
                bindingsMap.values()
        );
    }

}
