package team.unnamed.inject.test;

import team.unnamed.inject.Inject;
import team.unnamed.inject.Injector;
import team.unnamed.inject.InjectorFactory;
import team.unnamed.inject.identity.Key;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProtectedBindingsTest {

    @Test
    public void testProtectedBindings() {

        // doesn't have bindings
        Injector injector = InjectorFactory.create();
        // child...
        Injector childInjector = injector.createChildInjector(binder -> binder.bind(Foo.class).toInstance(new Foo(123)));

        Foo foo = injector.getInstance(Foo.class);
        assertNull(foo);

        foo = childInjector.getInstance(Foo.class);
        assertNotNull(foo);
        assertEquals(foo.n, 123);

    }

    @Test
    public void testGenericProtectedBindings() {

        Key<GenericFoo<Foo>> genericFooKey = new Key<GenericFoo<Foo>>() {};
        Injector injector = InjectorFactory.create();

        Injector childInjector = injector.createChildInjector(binder -> {
            binder.bind(Foo.class).toInstance(new Foo(79));
            binder.bind(genericFooKey).to(new Key<Bar<Foo>>() {});
        });

        GenericFoo<Foo> genericFoo = injector.getInstance(genericFooKey);

        assertNull(genericFoo);
        genericFoo = childInjector.getInstance(genericFooKey);

        assertNotNull(genericFoo);
        assertNotNull(genericFoo.get());
        assertEquals(79, genericFoo.get().n);

    }

    public interface GenericFoo<T> {

        T get();

    }

    public static class Bar<T> implements GenericFoo<T> {

        @Inject
        private T value;

        @Override
        public T get() {
            return value;
        }
    }

    public static class Foo {

        private final int n;

        public Foo(int n) {
            this.n = n;
        }
    }

}
