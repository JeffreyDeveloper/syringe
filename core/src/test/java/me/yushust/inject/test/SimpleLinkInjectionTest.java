package me.yushust.inject.test;

import me.yushust.inject.Injector;
import me.yushust.inject.InjectorFactory;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class SimpleLinkInjectionTest {

    @Test
    public void testInjection() {

        Injector injector = InjectorFactory.create(linker -> {
            linker.link(Foo.class).to(Bar.class);
        });

        Foo foo = injector.getInstance(Foo.class);
        assertNotNull(foo);

    }

    public interface Foo {}

    public static class Bar implements Foo {}

}
