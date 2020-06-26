package me.yushust.inject.test;

import me.yushust.inject.Inject;
import me.yushust.inject.Injector;
import me.yushust.inject.InjectorFactory;
import me.yushust.inject.Provider;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProviderInjectionTest {

    @Test
    public void injectProvider() {

        Injector injector = InjectorFactory.create(linker -> linker.bind(A.class).toProvider(() -> new A("a.")));

        B b = injector.getInstance(B.class);
        A a = b.aProvider.get();

        assertEquals(a.name, "a.");

    }

    public static class A {

        private final String name;

        public A (String name) {
            this.name = name;
        }

    }

    public static class B {

        @Inject private Provider<A> aProvider;

    }

}
