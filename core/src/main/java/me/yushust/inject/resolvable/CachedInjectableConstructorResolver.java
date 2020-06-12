package me.yushust.inject.resolvable;

import me.yushust.inject.cache.CacheAdapter;
import me.yushust.inject.cache.CacheAdapterBuilder;
import me.yushust.inject.identity.token.Token;

import java.lang.reflect.Constructor;

public class CachedInjectableConstructorResolver implements InjectableConstructorResolver {

    private final CacheAdapter<Token<?>, Constructor<?>> constructorCache;

    public CachedInjectableConstructorResolver(CacheAdapterBuilder cacheAdapterBuilder) {
        InjectableConstructorResolver delegate = new DefaultInjectableConstructorResolver();
        this.constructorCache = cacheAdapterBuilder.buildLoading(delegate::findInjectableConstructor);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Constructor<T> findInjectableConstructor(Token<T> type) {
        return (Constructor<T>) constructorCache.getOrLoad(type);
    }

}
