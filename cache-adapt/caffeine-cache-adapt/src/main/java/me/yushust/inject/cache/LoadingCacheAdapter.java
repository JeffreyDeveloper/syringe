package me.yushust.inject.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import java.util.concurrent.TimeUnit;

public class LoadingCacheAdapter<K, V> implements CacheAdapter<K, V> {

    private final LoadingCache<K, V> cache;

    public LoadingCacheAdapter(CacheLoader<K, V> loader, long expiry) {
        this.cache = Caffeine.newBuilder()
                .weakKeys()
                .expireAfterWrite(expiry, TimeUnit.MILLISECONDS)
                .build(loader::load);
    }

    @Override
    public V getOrLoad(K key) {
        return cache.get(key);
    }

}
