package me.yushust.inject.test;

import me.yushust.inject.Injector;
import me.yushust.inject.InjectorFactory;
import me.yushust.inject.scope.Scopes;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LinkedBindingTest {

    @Test
    public void testLinkedBindings() {

        Injector injector = InjectorFactory.create(binder -> {
            binder.bind(Bar.class).to(Foo.class).scope(Scopes.SINGLETON);
        });

        Bar bar = injector.getInstance(Bar.class);
        Bar bar2 = injector.getInstance(Bar.class);

        assertTrue(bar instanceof Foo);
        assertEquals(bar, bar2);

    }

    public interface Bar {

    }

    public static class Foo implements Bar {

        private final int id = (int) (Math.random() * 10);

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Foo foo = (Foo) o;
            return id == foo.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }

}
