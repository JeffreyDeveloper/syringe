package team.unnamed.inject.test;

import team.unnamed.inject.Inject;
import team.unnamed.inject.Injector;
import team.unnamed.inject.InjectorFactory;
import team.unnamed.inject.exception.InjectionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CyclicInjectionTest {

    @Test
    public void testInstanceBinding() {

        Foo foo = new Foo();

        Injector injector = InjectorFactory.create();

        try {
            injector.injectMembers(foo);
        } catch (InjectionException exception) {
            assertTrue(exception.getMessage().startsWith("Cyclic dependency detected!"));
        }
    }

    public static class Foo {

        @Inject
        private Bar bar;

    }

    public static class Bar {

        @Inject private Z z;

    }


    public static class Z {

        @Inject private Bar bar;

    }
}
