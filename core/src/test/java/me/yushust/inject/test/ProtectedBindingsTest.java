package me.yushust.inject.test;

import me.yushust.inject.Injector;
import me.yushust.inject.InjectorFactory;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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

        // double check the bindings
        foo = injector.getInstance(Foo.class);
        assertNull(foo);

    }

    public static class Foo {

        private final int n;

        public Foo(int n) {
            this.n = n;
        }
    }

}
