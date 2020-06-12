package me.yushust.inject;

import me.yushust.inject.link.Module;
import me.yushust.inject.cache.CacheAdapterBuilder;
import me.yushust.inject.internal.LinkerImpl;
import me.yushust.inject.internal.SimpleInjector;
import me.yushust.inject.resolvable.*;

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

        LinkerImpl linker = new LinkerImpl();

        for (Module module : modules) {
            module.configure(linker);
        }

        CacheAdapterBuilder cacheAdapterBuilder = options.getCacheAdapterBuilder();
        AnnotationTypeHandler annotationTypeHandler = new CachedAnnotationTypeHandler(cacheAdapterBuilder);
        InjectableConstructorResolver constructorResolver = new DefaultInjectableConstructorResolver(cacheAdapterBuilder);
        ParameterKeysResolver keysResolver = new MemberParameterKeysResolver(annotationTypeHandler);

        return new SimpleInjector(linker.getInternalLinker(), constructorResolver,
                keysResolver, cacheAdapterBuilder, annotationTypeHandler);

    }

}
