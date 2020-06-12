package me.yushust.inject.cache;

public interface CacheLoader<K, V> {

    V load(K key) throws Exception;

}
