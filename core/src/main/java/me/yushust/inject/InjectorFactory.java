package me.yushust.inject;

import me.yushust.inject.internal.BasicInternalBinder;
import me.yushust.inject.internal.InternalBinder;
import me.yushust.inject.process.ProcessorInterceptor;
import me.yushust.inject.bind.Module;
import me.yushust.inject.internal.SimpleInjector;
import me.yushust.inject.resolve.AnnotationTypeHandler;
import me.yushust.inject.resolve.resolver.*;

import java.util.Arrays;

public final class InjectorFactory {

    private InjectorFactory() {
        throw new UnsupportedOperationException("This class couldn't be instantiated!");
    }

    public static Injector create(Module... modules) {
        return create(InjectorOptions.builder().build(), modules);
    }

    public static Injector create(InjectorOptions options, Module... modules) {
        return create(options, Arrays.asList(modules));
    }

    public static Injector create(Iterable<Module> modules) {
        return create(InjectorOptions.builder().build(), modules);
    }

    public static Injector create(InjectorOptions options, Iterable<Module> modules) {

        InternalBinder linker = new BasicInternalBinder();

        for (Module module : modules) {
            module.configure(linker);
        }

        ProcessorInterceptor interceptor = options.getProcessorInterceptor();

        AnnotationTypeHandler annotationTypeHandler = new AnnotationTypeHandler();
        InjectableConstructorResolver constructorResolver = interceptor
                .interceptConstructorResolver(new ReflectionInjectableConstructorResolver());
        MemberKeyResolver memberKeyResolver = new ReflectionMemberKeyResolver(annotationTypeHandler);
        InjectableMembersResolver injectableMembersResolver = interceptor.interceptMembersResolver(
                        new ReflectionInjectableMembersResolver(options.getOptionalInjectionChecker(), memberKeyResolver)
        );

        return new SimpleInjector(linker, constructorResolver, injectableMembersResolver, interceptor);
    }

}
