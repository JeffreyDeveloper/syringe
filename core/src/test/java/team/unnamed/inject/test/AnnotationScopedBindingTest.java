package team.unnamed.inject.test;

import team.unnamed.inject.Injector;
import team.unnamed.inject.InjectorFactory;
import team.unnamed.inject.InjectorOptions;
import team.unnamed.inject.process.annotation.Singleton;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AnnotationScopedBindingTest {

    @Test
    public void testScoped() {

        Injector injector = InjectorFactory.create(
                InjectorOptions.builder()
                        .enableTypeScopingAnnotations()
                        .build()
        );

        Foo foo = injector.getInstance(Foo.class);
        Foo foo2 = injector.getInstance(Foo.class);

        assertNotNull(foo);
        assertEquals(foo, foo2);

    }

    @Singleton
    public static class Foo {

        private final UUID id = UUID.randomUUID();

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Foo foo = (Foo) o;
            return Objects.equals(id, foo.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }

}
