package team.unnamed.inject.process;

import team.unnamed.inject.Provider;
import team.unnamed.inject.bind.Binding;
import team.unnamed.inject.bind.PrivateBinder;
import team.unnamed.inject.identity.Key;
import team.unnamed.inject.internal.InternalBinder;
import team.unnamed.inject.internal.bind.LinkedBinding;
import team.unnamed.inject.internal.bind.ProviderKeyBinding;
import team.unnamed.inject.process.annotation.Expose;
import team.unnamed.inject.process.annotation.ImplementedBy;
import team.unnamed.inject.process.annotation.ProvidedBy;

public class DefaultBindingAnnotationProcessor implements BindingAnnotationProcessor {

    @Override
    @SuppressWarnings("unchecked")
    public <T> boolean bind(InternalBinder binder, Class<T> clazz) {

        Key<T> key = Key.of(clazz);
        Binding<T> binding = binder.findBinding(key);

        boolean boundByAnnotations = false;

        if (binding == null) {

            ImplementedBy implementedBySpecification = clazz.getAnnotation(ImplementedBy.class);

            if (implementedBySpecification != null) {
                binding = new LinkedBinding<>(key, Key.of(
                        (Class<? extends T>) implementedBySpecification.value())
                );
                binder.setBinding(binding);
                boundByAnnotations = true;
            }

            ProvidedBy providedBySpecification = clazz.getAnnotation(ProvidedBy.class);

            if (providedBySpecification != null && !boundByAnnotations) {
                binding = new ProviderKeyBinding<>(key,
                        (Class<? extends Provider<? extends T>>) providedBySpecification.value()
                );
                binder.setBinding(binding);
                boundByAnnotations = true;
            }
        }

        binding = binder.findBinding(key); // find the binding again

        if (binding != null) {
            if (binder instanceof PrivateBinder) {
                if (clazz.isAnnotationPresent(Expose.class)) {
                    PrivateBinder privateBinder = (PrivateBinder) binder;
                    privateBinder.expose(key);
                }
            }
        }

        return boundByAnnotations;

    }

}
