package me.yushust.inject.test;

import me.yushust.inject.Inject;
import me.yushust.inject.Injector;
import me.yushust.inject.InjectorFactory;
import me.yushust.inject.InjectorOptions;
import me.yushust.inject.process.annotation.ImplementedBy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AnnotationBindingTest {

    @Test
    public void testBindingAnnotations() {

        Injector injector = InjectorFactory.create(
                InjectorOptions.builder()
                        .enableBindingAnnotations() // enabled the processing of ImplementedBy and ProvidedBy annotations
                        .build()
        );

        Foo foo = injector.getInstance(Foo.class);

        assertNotNull(foo);
        assertNotNull(((Bar) foo).s);

    }

    @ImplementedBy(Bar.class)
    public interface Foo {

    }

    public static class Bar implements Foo {

        @Inject private S s;

    }

    public static class S {

    }

}
