package me.yushust.inject.internal.bind;

import me.yushust.inject.Inject;
import me.yushust.inject.Injector;
import me.yushust.inject.Provider;
import me.yushust.inject.identity.Key;

public class ProviderKeyBinding<T> extends SimpleBinding<T> {

    public ProviderKeyBinding(Key<T> key, Class<? extends Provider<? extends T>> provider) {
        super(key, new BindingProvider<>(provider));
    }

    public static class BindingProvider<T> implements Provider<T> {

        private final Class<? extends Provider<? extends T>> providerKey;
        @Inject private Injector injector;

        public BindingProvider(Class<? extends Provider<? extends T>> providerKey) {
            this.providerKey = providerKey;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T get() {
            Provider<T> provider = (Provider<T>) injector.getInstance(providerKey);
            if (provider == null) {
                return null;
            }
            return provider.get();
        }
    }

}
