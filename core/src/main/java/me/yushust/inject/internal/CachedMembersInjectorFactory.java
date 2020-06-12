package me.yushust.inject.internal;

import me.yushust.inject.Injector;
import me.yushust.inject.cache.CacheAdapter;
import me.yushust.inject.cache.CacheAdapterBuilder;
import me.yushust.inject.identity.token.Token;
import me.yushust.inject.resolvable.AnnotationTypeHandler;
import me.yushust.inject.resolvable.ParameterKeysResolver;

public class CachedMembersInjectorFactory implements MembersInjectorFactory {

    private final CacheAdapter<Token<?>, MembersInjector> membersInjectorCache;

    public CachedMembersInjectorFactory(Injector injector, ParameterKeysResolver keysResolver,
                                        AnnotationTypeHandler annotationTypeHandler, CacheAdapterBuilder cacheBuilder) {
        MembersInjectorFactory delegate = new SimpleMembersInjectorFactory(
                annotationTypeHandler, keysResolver, injector
        );
        this.membersInjectorCache = cacheBuilder.buildLoading(delegate::getMembersInjector);
    }

    @Override
    public MembersInjector getMembersInjector(Token<?> key) {
        return membersInjectorCache.getOrLoad(key);
    }

}
