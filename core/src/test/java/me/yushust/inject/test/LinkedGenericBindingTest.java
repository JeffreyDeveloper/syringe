package me.yushust.inject.test;

import me.yushust.inject.Injector;
import me.yushust.inject.InjectorFactory;
import me.yushust.inject.identity.Key;
import me.yushust.inject.scope.Scopes;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LinkedGenericBindingTest {

    @Test
    public void testGenericBindings() {

        Injector injector = InjectorFactory.create(binder -> {
            binder.bind(new Key<Bar<String>>() {}).to(new Key<Foo<String>>() {}).scope(Scopes.SINGLETON);
        });

        Bar<String> bar = injector.getInstance(new Key<Bar<String>>() {});
        assertNotNull(bar);

    }

    public interface Bar<T> {
    }

    public static class Foo<T> implements Bar<T> {
    }

}
