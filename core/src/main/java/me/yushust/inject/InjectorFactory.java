package me.yushust.inject;

import me.yushust.inject.internal.InternalLinker;
import me.yushust.inject.link.Module;
import me.yushust.inject.cache.CacheAdapterBuilder;
import me.yushust.inject.internal.BasicInternalLinker;
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

        InternalLinker linker = new BasicInternalLinker();

        for (Module module : modules) {
            module.configure(linker);
        }

        CacheAdapterBuilder cacheAdapterBuilder = options.getCacheAdapterBuilder();
        AnnotationTypeHandler annotationTypeHandler = new CachedAnnotationTypeHandler(cacheAdapterBuilder);
        InjectableConstructorResolver constructorResolver = new CachedInjectableConstructorResolver(cacheAdapterBuilder);
        ParameterKeysResolver keysResolver = new MemberParameterKeysResolver(annotationTypeHandler);

        return new SimpleInjector(linker, constructorResolver,
                keysResolver, cacheAdapterBuilder, annotationTypeHandler);

    }

}
