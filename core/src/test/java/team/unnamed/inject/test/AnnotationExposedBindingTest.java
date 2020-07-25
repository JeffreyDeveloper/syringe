package team.unnamed.inject.test;

import team.unnamed.inject.Inject;
import team.unnamed.inject.Injector;
import team.unnamed.inject.InjectorFactory;
import team.unnamed.inject.InjectorOptions;
import team.unnamed.inject.name.Named;
import team.unnamed.inject.process.annotation.Expose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AnnotationExposedBindingTest {

    @Test
    public void testExpose() {

        Injector parentInjector = InjectorFactory.create(
                InjectorOptions.builder()
                        .enableTypeBindingAnnotations()
                        .build(),
                binder -> binder.bind(String.class).named("name").toInstance("bar")
        );

        Injector childInjector = parentInjector.createChildInjector(
                binder -> binder.bind(Foo.class).to(FooImpl.class)
        );

        Foo foo = childInjector.getInstance(Foo.class);

        assertNotNull(foo);
        assertNotNull(foo.getBar());

        foo = parentInjector.getInstance(Foo.class);

        assertNotNull(foo);
        assertNotNull(foo.getBar());

    }

    public static class Bar {
        @Inject
        @Named("name") private String name;
    }

    @Expose
    public interface Foo {

        Bar getBar();

    }

    public static class FooImpl implements Foo {

        @Inject private Bar bar;

        @Override
        public Bar getBar() {
            return bar;
        }
    }

}
