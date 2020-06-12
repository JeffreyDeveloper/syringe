package me.yushust.inject.cache;

import static me.yushust.inject.internal.Preconditions.*;

import java.util.concurrent.TimeUnit;

public class CaffeineCacheAdapterBuilder implements CacheAdapterBuilder {

    private final long expiry;

    public CaffeineCacheAdapterBuilder(TimeUnit expiryUnit, long expiryTime) {

        checkNotNull(expiryUnit);
        checkArgument(expiryTime > 0, "Expiry time couldn't be minor than 0");

        this.expiry = expiryUnit.toMillis(expiryTime);

    }

    @Override
    public <K, V> CacheAdapter<K, V> buildLoading(CacheLoader<K, V> loader) {

        checkNotNull(loader);
        return new LoadingCacheAdapter<>(loader, expiry);
    }

}
