package me.yushust.inject.process;

import me.yushust.inject.Provider;
import me.yushust.inject.bind.Binding;
import me.yushust.inject.identity.Key;
import me.yushust.inject.internal.InternalBinder;
import me.yushust.inject.internal.bind.LinkedBinding;
import me.yushust.inject.internal.bind.ProviderKeyBinding;
import me.yushust.inject.process.annotation.ImplementedBy;
import me.yushust.inject.process.annotation.ProvidedBy;

public class DefaultBindingAnnotationProcessor implements BindingAnnotationProcessor {

    @Override
    @SuppressWarnings("unchecked")
    public <T> boolean bind(InternalBinder binder, Class<T> clazz) {

        Key<T> key = Key.of(clazz);
        Binding<T> binding = binder.findBinding(key);

        if (binding != null) {
            return false;
        }

        ImplementedBy implementedBySpecification = clazz.getAnnotation(ImplementedBy.class);

        if (implementedBySpecification != null) {
            binding = new LinkedBinding<>(key, Key.of(
                    (Class<? extends T>) implementedBySpecification.value())
            );
            binder.setBinding(binding);
            return true;
        }

        ProvidedBy providedBySpecification = clazz.getAnnotation(ProvidedBy.class);

        if (providedBySpecification != null) {
            binding = new ProviderKeyBinding<>(key,
                    (Class<? extends Provider<? extends T>>) providedBySpecification.value()
            );
            binder.setBinding(binding);
            return true;
        }

        return false;

    }

}
