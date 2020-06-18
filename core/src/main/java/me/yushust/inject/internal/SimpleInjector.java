package me.yushust.inject.internal;

import me.yushust.inject.Injector;
import me.yushust.inject.Provider;
import me.yushust.inject.internal.injector.ConstructorInjector;
import me.yushust.inject.process.ProcessorInterceptor;
import me.yushust.inject.link.Link;
import me.yushust.inject.exception.UnsupportedInjectionException;
import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.token.Token;
import me.yushust.inject.link.Module;
import me.yushust.inject.resolve.resolver.InjectableConstructorResolver;
import me.yushust.inject.resolve.resolver.InjectableMembersResolver;
import me.yushust.inject.util.Providers;

import java.util.Objects;

import static me.yushust.inject.internal.Preconditions.checkNotNull;

public class SimpleInjector implements Injector {

    private final InternalLinker linker;
    private final InjectableConstructorResolver injectableConstructorResolver;
    private final MembersInjectorFactory membersInjectorFactory;

    public SimpleInjector(InternalLinker linker, InjectableConstructorResolver injectableConstructorResolver,
                          InjectableMembersResolver membersResolver, ProcessorInterceptor processorInterceptor) {
        this.linker = checkNotNull(linker);
        this.injectableConstructorResolver = checkNotNull(injectableConstructorResolver);
        this.membersInjectorFactory = processorInterceptor.interceptMembersInjectorFactory(new SimpleMembersInjectorFactory(
                injectableConstructorResolver, membersResolver, this
        ));
    }

    private SimpleInjector(InternalLinker linker, SimpleInjector prototype) {
        this.linker = linker;
        this.injectableConstructorResolver = prototype.injectableConstructorResolver;
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
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> T getInstance(Key<T> key) {

        Objects.requireNonNull(key);

        if (key.getType().getRawType() == Injector.class) {
            return (T) this;
        }

        if (key.getType().getRawType() == Provider.class) {
            Key<?> providerKey = Providers.keyOfProvider((Key) key);
            Provider<?> provider = getProvider(providerKey);
            if (provider != null) {
                return (T) provider;
            }
            return null;
        }

        Provider<T> provider = getProvider(key);
        if (provider != null) {
            return provider.get();
        }

        ConstructorInjector<T> constructorInjector = membersInjectorFactory.getConstructorInjector(key.getType());
        T instance = constructorInjector.createInstance();

        if (instance != null) {
            injectMembers(instance);
        }

        return instance;

    }

    @Override
    public Injector createChildInjector(Iterable<Module> modules) {

        InternalLinker isolatedLinker = new IsolatedInternalLinker(linker);
        for (Module module : modules) {
            module.configure(isolatedLinker);
        }
        return new SimpleInjector(isolatedLinker, this);

    }

    private <T> Provider<T> getProvider(Key<T> key) {

        Link<T> link = linker.findLink(key);

        if (link == null) {
            return null;
        }

        if (!link.isProviderInjected()) {
            Provider<T> provider = link.getProvider();
            injectMembers(provider);
            link.updateProvider(provider);
            link.setProviderInjected(true);
            linker.setLink(link);
        }

        return link.getProvider();

    }

}
