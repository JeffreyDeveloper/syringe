package me.yushust.inject.test;

import me.yushust.inject.Inject;
import me.yushust.inject.Injector;
import me.yushust.inject.InjectorFactory;
import me.yushust.inject.exception.InjectionException;
import org.junit.Test;

import static org.junit.Assert.*;

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

        @Inject private Bar bar;

    }

    public static class Bar {

        @Inject private Z z;

    }


    public static class Z {

        @Inject private Bar bar;

    }
}
