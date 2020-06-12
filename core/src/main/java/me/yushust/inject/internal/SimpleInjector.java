package me.yushust.inject.internal;

import me.yushust.inject.Injector;
import me.yushust.inject.Provider;
import me.yushust.inject.link.Link;
import me.yushust.inject.cache.CacheAdapterBuilder;
import me.yushust.inject.exception.NoInjectableConstructorException;
import me.yushust.inject.exception.UnsupportedInjectionException;
import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.token.Token;
import me.yushust.inject.link.Module;
import me.yushust.inject.resolvable.AnnotationTypeHandler;
import me.yushust.inject.resolvable.InjectableConstructorResolver;
import me.yushust.inject.resolvable.ParameterKeysResolver;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;

public class SimpleInjector implements Injector {

    private final InternalLinker linker;
    private final InjectableConstructorResolver injectableConstructorResolver;
    private final ParameterKeysResolver keyResolver;
    private final MembersInjectorFactory membersInjectorFactory;

    public SimpleInjector(InternalLinker linker, InjectableConstructorResolver injectableConstructorResolver,
                          ParameterKeysResolver keyResolver, CacheAdapterBuilder cacheBuilder,
                          AnnotationTypeHandler annotationTypeHandler) {
        this.linker = linker;
        this.injectableConstructorResolver = injectableConstructorResolver;
        this.keyResolver = keyResolver;
        this.membersInjectorFactory = new CachedMembersInjectorFactory(this, keyResolver, annotationTypeHandler, cacheBuilder);
    }

    private SimpleInjector(InternalLinker linker, SimpleInjector prototype) {
        this.linker = linker;
        this.injectableConstructorResolver = prototype.injectableConstructorResolver;
        this.keyResolver = prototype.keyResolver;
        this.membersInjectorFactory = prototype.membersInjectorFactory;
    }

    @Override
    public void injectMembers(Object object) {

        Objects.requireNonNull(object);

        Class<?> clazz = object.getClass();
        Token<?> token = new Token<>(clazz);
        MembersInjector injector = membersInjectorFactory.getMembersInjector(token);

        try {
            injector.injectMembers(object);
        } catch (UnsupportedInjectionException e) {
            e.printStackTrace();
        }

    }

    @Override
    public <T> T getInstance(Class<T> type) {
        return getInstance(Key.of(type));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getInstance(Key<T> key) {

        Objects.requireNonNull(key);

        if (key.getType().getRawType() == Injector.class) {
            return (T) this;
        }

        Link<T> link = linker.findLink(key);

        if (link != null) {
            if (!link.isProviderInjected()) {
                Provider<T> provider = link.getProvider();
                injectMembers(provider);
                link.updateProvider(provider);
                link.setProviderInjected(true);
                linker.setLink(link);
            }
            return link.getProvider().get();
        }

        try {

            Constructor<T> constructor = injectableConstructorResolver.findInjectableConstructor(key.getType());
            List<Key<?>> injections = keyResolver.resolveParameterKeys(constructor);
            Object[] parameters = new Object[injections.size()];


            for (int i = 0; i < injections.size(); i++) {
                Key<?> injection = injections.get(i);
                Link<?> parameterLink = linker.findLink(injection);

                if (parameterLink == null) {
                    Object instance = getInstance(injection);

                    if (instance == null) {
                        throw new IllegalStateException("No instances for " + injection.toString());
                    }

                    parameters[i] = instance;
                    continue;
                }

                parameters[i] = parameterLink.getProvider().get();
            }

            T instance = constructor.newInstance(parameters);
            injectMembers(instance);
            return instance;

        } catch (NoInjectableConstructorException | UnsupportedInjectionException |
                IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Injector createChildInjector(Iterable<Module> modules) {
        SimpleIsolatedLinker isolatedLinker = SimpleIsolatedLinker.create(linker);
        for (Module module : modules) {
            module.configure(isolatedLinker);
        }
        return new SimpleInjector(isolatedLinker.getInternalLinker(), this);
    }

}
