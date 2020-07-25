package team.unnamed.inject.test;

import team.unnamed.inject.Injector;
import team.unnamed.inject.InjectorFactory;
import team.unnamed.inject.identity.Key;
import team.unnamed.inject.scope.Scopes;
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
