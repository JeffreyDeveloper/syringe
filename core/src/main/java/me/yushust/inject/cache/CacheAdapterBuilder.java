package me.yushust.inject.cache;

public interface CacheAdapterBuilder {

    <K, V> CacheAdapter<K, V> buildLoading(CacheLoader<K, V> loader);

}
