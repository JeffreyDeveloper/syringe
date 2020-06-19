package me.yushust.inject.internal;

import me.yushust.inject.Injector;
import me.yushust.inject.Provider;
import me.yushust.inject.internal.injector.ConstructorInjector;
import me.yushust.inject.process.ProcessorInterceptor;
import me.yushust.inject.bind.Binding;
import me.yushust.inject.exception.UnsupportedInjectionException;
import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.token.Token;
import me.yushust.inject.bind.Module;
import me.yushust.inject.resolve.resolver.InjectableConstructorResolver;
import me.yushust.inject.resolve.resolver.InjectableMembersResolver;
import me.yushust.inject.util.Providers;

import java.util.Objects;

import static me.yushust.inject.internal.Preconditions.checkNotNull;

public class SimpleInjector implements Injector {

    private final InternalBinder binder;
    private final InjectableConstructorResolver injectableConstructorResolver;
    private final MembersInjectorFactory membersInjectorFactory;

    public SimpleInjector(InternalBinder binder, InjectableConstructorResolver injectableConstructorResolver,
                          InjectableMembersResolver membersResolver, ProcessorInterceptor processorInterceptor) {
        this.binder = checkNotNull(binder);
        this.injectableConstructorResolver = checkNotNull(injectableConstructorResolver);
        this.membersInjectorFactory = processorInterceptor.interceptMembersInjectorFactory(new SimpleMembersInjectorFactory(
                injectableConstructorResolver, membersResolver, this
        ));
    }

    private SimpleInjector(InternalBinder binder, SimpleInjector prototype) {
        this.binder = binder;
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
            return (T) provider;
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

        InternalBinder isolatedLinker = new PrivateInternalBinder(binder);
        for (Module module : modules) {
            module.configure(isolatedLinker);
        }
        return new SimpleInjector(isolatedLinker, this);

    }

    private <T> Provider<T> getProvider(Key<T> key) {

        Binding<T> binding = binder.findBinding(key);

        if (binding == null) {
            return null;
        }

        if (!binding.isProviderInjected()) {
            Provider<T> provider = binding.getProvider();
            injectMembers(provider);
            binding.updateProvider(provider);
            binding.setProviderInjected(true);
            binder.setBinding(binding);
        }

        return binding.getProvider();

    }

}
