package me.yushust.inject.cache;

public interface CacheAdapter<K, V> {

    V getOrLoad(K key);

}
