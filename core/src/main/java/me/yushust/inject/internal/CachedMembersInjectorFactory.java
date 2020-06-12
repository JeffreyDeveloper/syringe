package me.yushust.inject.internal;

import me.yushust.inject.Injector;
import me.yushust.inject.cache.CacheAdapter;
import me.yushust.inject.cache.CacheAdapterBuilder;
import me.yushust.inject.identity.token.Token;
import me.yushust.inject.internal.injector.ConstructorInjector;
import me.yushust.inject.resolvable.AnnotationTypeHandler;
import me.yushust.inject.resolvable.InjectableConstructorResolver;
import me.yushust.inject.resolvable.ParameterKeysResolver;

public class CachedMembersInjectorFactory implements MembersInjectorFactory {

    private final CacheAdapter<Token<?>, MembersInjector> membersInjectorCache;
    private final CacheAdapter<Token<?>, ConstructorInjector<?>> constructorInjectorCache;

    public CachedMembersInjectorFactory(Injector injector, ParameterKeysResolver keysResolver, InjectableConstructorResolver constructorResolver,
                                        AnnotationTypeHandler annotationTypeHandler, CacheAdapterBuilder cacheBuilder) {
        MembersInjectorFactory delegate = new SimpleMembersInjectorFactory(
                annotationTypeHandler, constructorResolver, keysResolver, injector
        );
        this.membersInjectorCache = cacheBuilder.buildLoading(delegate::getMembersInjector);
        this.constructorInjectorCache = cacheBuilder.buildLoading(delegate::getConstructorInjector);
    }

    @Override
    public MembersInjector getMembersInjector(Token<?> key) {
        return membersInjectorCache.getOrLoad(key);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> ConstructorInjector<T> getConstructorInjector(Token<T> key) {
        return (ConstructorInjector<T>) constructorInjectorCache.getOrLoad(key);
    }

}
