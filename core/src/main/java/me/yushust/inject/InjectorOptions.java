package me.yushust.inject;

import me.yushust.inject.cache.CacheAdapterBuilder;
import me.yushust.inject.cache.DynamicAdapterBuilder;

import java.util.Objects;

public class InjectorOptions {

    private final CacheAdapterBuilder cacheAdapterBuilder;

    public InjectorOptions(CacheAdapterBuilder cacheAdapterBuilder) {
        this.cacheAdapterBuilder = cacheAdapterBuilder;
    }

    public CacheAdapterBuilder getCacheAdapterBuilder() {
        return cacheAdapterBuilder;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private CacheAdapterBuilder cacheAdapterBuilder = new DynamicAdapterBuilder();

        public Builder cacheAdapterBuilder(CacheAdapterBuilder cacheAdapterBuilder) {
            Objects.requireNonNull(cacheAdapterBuilder);
            this.cacheAdapterBuilder = cacheAdapterBuilder;
            return this;
        }

        public InjectorOptions build() {
            return new InjectorOptions(cacheAdapterBuilder);
        }

    }

}
