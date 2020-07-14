package me.yushust.inject.process;

import me.yushust.inject.bind.Binding;
import me.yushust.inject.identity.Key;
import me.yushust.inject.internal.InternalBinder;
import me.yushust.inject.internal.bind.LinkedBinding;
import me.yushust.inject.internal.bind.SimpleBinding;
import me.yushust.inject.process.annotation.Singleton;
import me.yushust.inject.scope.Scope;
import me.yushust.inject.scope.Scopes;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DefaultScopeAnnotationProcessor implements ScopeAnnotationProcessor {

    private static final Map<Class<? extends Annotation>, Scope> SCOPES_BY_ANNOTATION;

    static {
        Map<Class<? extends Annotation>, Scope> modifiableScopesByAnnotation = new HashMap<>();

        modifiableScopesByAnnotation.put(javax.inject.Singleton.class, Scopes.SINGLETON);
        modifiableScopesByAnnotation.put(Singleton.class, Scopes.SINGLETON);

        SCOPES_BY_ANNOTATION = Collections.unmodifiableMap(modifiableScopesByAnnotation);
    }

    @Override
    public <T> boolean scope(InternalBinder binder, Class<T> clazz) {

        Key<T> key = Key.of(clazz);
        Binding<T> binding = binder.findBinding(key);

        if (binding == null && !clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers())) {
            binding = new LinkedBinding<>(key, key);
        }

        if (binding == null) {
            return false;
        }

        for (Map.Entry<Class<? extends Annotation>, Scope> scopeEntry : SCOPES_BY_ANNOTATION.entrySet()) {

            Class<? extends Annotation> annotation = scopeEntry.getKey();
            Scope scope = scopeEntry.getValue();

            if (clazz.isAnnotationPresent(annotation)) {
                binding = new SimpleBinding<>(
                        key,
                        scope.wrap(key, binding.getProvider())
                );
                binder.setBinding(binding);
                return true;
            }

        }

        return false;
    }

}
