package team.unnamed.inject.test;

import team.unnamed.inject.Inject;
import team.unnamed.inject.Injector;
import team.unnamed.inject.InjectorFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InstanceBindingTest {

    @Test
    public void testInstanceBinding() {

        Foo foo = new Foo();

        Injector injector = InjectorFactory.create(binder -> {
            binder.bind(Foo.class).toInstance(foo);
        });

        injector.injectMembers(foo);
        assertNotNull(foo.bar);
        assertEquals(foo, foo.bar.foo);

    }

    public static class Foo {

        @Inject
        private Bar bar;

    }

    public static class Bar {

        @Inject private Foo foo;

    }

}
