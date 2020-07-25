package team.unnamed.inject.test;

import team.unnamed.inject.Inject;
import team.unnamed.inject.Injector;
import team.unnamed.inject.InjectorFactory;
import team.unnamed.inject.InjectorOptions;
import team.unnamed.inject.process.annotation.ImplementedBy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AnnotationBindingTest {

    @Test
    public void testBindingAnnotations() {

        Injector injector = InjectorFactory.create(
                InjectorOptions.builder()
                        .enableTypeBindingAnnotations() // enabled the processing of ImplementedBy and ProvidedBy annotations
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

        @Inject
        private S s;

    }

    public static class S {

    }

}
