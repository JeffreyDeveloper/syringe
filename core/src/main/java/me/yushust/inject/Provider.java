package me.yushust.inject;

import me.yushust.inject.bind.Binding;
import me.yushust.inject.scope.Scope;

/**
 * A functional interface that provides instances can be wrapped
 * with a {@link Scope} (like {@code Scopes.SINGLETON})
 * @param <T> The type of instance that will be provided
 * @see Binding
 */
public interface Provider<T> {

    /**
     * Get the instance that this Provider gives you. It can always
     * be a new instance or always the same instance, depending on
     * the Scope and as defined by the user
     * @return An object
     */
    T get();

}
