package me.yushust.inject.test;

import me.yushust.inject.Injector;
import me.yushust.inject.InjectorFactory;
import me.yushust.inject.Provider;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProviderKeyBindingTest {

    @Test
    public void inject() {

        Injector injector = InjectorFactory.create(binder -> {
            binder.bind(Foo.class).toProvider(FooProvider.class);
        });

        Foo foo = injector.getInstance(Foo.class);

        assertEquals(foo.id, "test-id");

    }

    public static class FooProvider implements Provider<Foo> {

        @Override
        public Foo get() {
            return new Foo("test-id");
        }

    }

    public static class Foo {

        private final String id;

        public Foo(String id) {
            this.id = id;
        }
    }

}
