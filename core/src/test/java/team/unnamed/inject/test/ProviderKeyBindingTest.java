package team.unnamed.inject.test;

import team.unnamed.inject.Injector;
import team.unnamed.inject.InjectorFactory;
import team.unnamed.inject.Provider;
import team.unnamed.inject.scope.Scopes;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProviderKeyBindingTest {

    @Test
    public void testProviderInjection() {

        Injector injector = InjectorFactory.create(binder -> {
            binder.bind(Foo.class).toProvider(FooProvider.class);
        });

        Foo foo = injector.getInstance(Foo.class);

        assertEquals(foo.id, "test-id");

    }

    @Test
    public void testSingletonProviderInjection() {

        Injector injector = InjectorFactory.create(binder -> {
            binder.bind(Foo.class).toProvider(FooProvider.class).scope(Scopes.SINGLETON);
        });

        Foo foo = injector.getInstance(Foo.class);
        Foo foo2 = injector.getInstance(Foo.class);

        assertEquals(foo, foo2);

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
