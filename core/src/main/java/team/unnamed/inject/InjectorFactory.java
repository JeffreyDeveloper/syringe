package team.unnamed.inject;

import team.unnamed.inject.internal.InternalBinder;
import team.unnamed.inject.internal.SimpleBinder;
import team.unnamed.inject.process.BindingAnnotationProcessor;
import team.unnamed.inject.process.ProcessorInterceptor;
import team.unnamed.inject.bind.Module;
import team.unnamed.inject.internal.SimpleInjector;
import team.unnamed.inject.process.ScopeAnnotationProcessor;
import team.unnamed.inject.resolve.AnnotationTypeHandler;
import team.unnamed.inject.resolve.resolver.*;

import java.util.Arrays;

/**
 * A factory to create injectors in a very simple way
 * <p>
 *     Module myModule = new MyModule();
 *     Injector injector = InjectorFactory.create(myModule);
 * </p>
 * You can also use more options using {@link InjectorOptions}
 * <p>
 *
 *     Module myModule = new MyModule();
 *     Injector injector = InjectorFactory.create(
 *          InjectorOptions.builder()
 *              .optionalInjectionChecker(new MyOptionalInjectionChecker())
 *              .build(),
 *          myModule
 *     );
 *
 * </p>
 * @see Injector
 */
public final class InjectorFactory {

    private InjectorFactory() {
        throw new UnsupportedOperationException("This class couldn't be instantiated!");
    }

    /**
     * Create an {@link Injector} with the given Modules.
     * Equivalent to {@link InjectorFactory#create(InjectorOptions, Module...)}
     * using default {@link InjectorOptions}
     * @param modules Injector configuration modules
     * @return The injector
     */
    public static Injector create(Module... modules) {
        return create(InjectorOptions.builder().build(), modules);
    }

    /**
     * Create an {@link Injector} with the given Modules and given Injector Options
     * Equivalent to {@link InjectorFactory#create(InjectorOptions, Iterable)}
     * @param options Injector options
     * @param modules Injector configuration modules
     * @return The injector
     */
    public static Injector create(InjectorOptions options, Module... modules) {
        return create(options, Arrays.asList(modules));
    }

    /**
     * Create an {@link Injector} with the given Modules.
     * Equivalent to {@link InjectorFactory#create(InjectorOptions, Iterable)}
     * using default {@link InjectorOptions}
     * @param modules Injector configuration modules
     * @return The injector
     */
    public static Injector create(Iterable<Module> modules) {
        return create(InjectorOptions.builder().build(), modules);
    }

    /**
     * Create an {@link Injector} with the given Modules and the given options.
     * @param options Injector options
     * @param modules Injector configuration modules
     * @return The injector
     */
    public static Injector create(InjectorOptions options, Iterable<Module> modules) {

        InternalBinder binder = new SimpleBinder();

        for (Module module : modules) {
            module.configure(binder);
        }

        ProcessorInterceptor interceptor = options.getProcessorInterceptor();

        AnnotationTypeHandler annotationTypeHandler = new AnnotationTypeHandler();
        BindingAnnotationProcessor bindingAnnotationProcessor = options.getBindingAnnotationProcessor();
        ScopeAnnotationProcessor scopeAnnotationProcessor = options.getScopeAnnotationProcessor();
        MemberKeyResolver memberKeyResolver = new ReflectionMemberKeyResolver(annotationTypeHandler);
        InjectableConstructorResolver constructorResolver = new ReflectionInjectableConstructorResolver();
        constructorResolver = interceptor.interceptConstructorResolver(constructorResolver);

        InjectableMembersResolver injectableMembersResolver = new ReflectionInjectableMembersResolver(
                options.getOptionalInjectionChecker(), memberKeyResolver, options.requiresResolveAnnotation()
        );
        injectableMembersResolver = interceptor.interceptMembersResolver(injectableMembersResolver);

        return new SimpleInjector(
                binder, constructorResolver, injectableMembersResolver, interceptor,
                bindingAnnotationProcessor, scopeAnnotationProcessor
        );
    }

}
