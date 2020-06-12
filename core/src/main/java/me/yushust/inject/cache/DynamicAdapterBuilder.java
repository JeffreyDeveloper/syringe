package me.yushust.inject.cache;

/**
 * Create a dynamic adapter. Does not cache data
 */
public class DynamicAdapterBuilder implements CacheAdapterBuilder {

    @Override
    public <K, V> CacheAdapter<K, V> buildLoading(CacheLoader<K, V> loader) {
        return new DynamicAdapter<>(loader);
    }

    public static class DynamicAdapter<K, V> implements CacheAdapter<K, V> {

        private final CacheLoader<K, V> cacheLoader;

        public DynamicAdapter(CacheLoader<K, V> cacheLoader) {
            this.cacheLoader = cacheLoader;
        }

        @Override
        public V getOrLoad(K key) {

            try {
                return cacheLoader.load(key);
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

    }

}
