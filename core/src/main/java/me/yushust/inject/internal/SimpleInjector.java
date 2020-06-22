package me.yushust.inject.internal;

import me.yushust.inject.Injector;
import me.yushust.inject.Provider;
import me.yushust.inject.identity.token.Token;
import me.yushust.inject.internal.injector.ConstructorInjector;
import me.yushust.inject.process.ProcessorInterceptor;
import me.yushust.inject.bind.Binding;
import me.yushust.inject.exception.UnsupportedInjectionException;
import me.yushust.inject.identity.Key;
import me.yushust.inject.bind.Module;
import me.yushust.inject.resolve.resolver.InjectableConstructorResolver;
import me.yushust.inject.resolve.resolver.InjectableMembersResolver;
import me.yushust.inject.util.Providers;

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
        this.membersInjectorFactory = prototype.membersInjectorFactory.usingInjector(this);
    }

    @Override
    public void injectMembers(Object object) {
        checkNotNull(object);
        injectMembers(new Token<>(object.getClass()), object);
    }

    @Override
    public <T> void injectMembers(Token<T> token, T instance) {
        checkNotNull(token);
        checkNotNull(instance);

        MembersInjector injector = membersInjectorFactory.getMembersInjector(token);

        try {
            injector.injectMembers(instance);
        } catch (UnsupportedInjectionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> T getInstance(Class<T> type) {
        return getInstance(new Token<>(type));
    }

    @Override
    public <T> T getInstance(Token<T> token) {
        return getInstance(Key.of(token));
    }

    @Override
    public <T> T getInstance(Key<T> key) {
        return getInstance(key, false);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> T getInstance(Key<T> key, boolean ignoreExplicitBindings) {
        checkNotNull(key);
        Token<T> type = key.getType();

        if (type.getRawType() == Injector.class) {
            return (T) this;
        }

        if (type.getRawType() == Provider.class) {
            Key<?> providerKey = Providers.keyOfProvider((Key) key);
            Provider<?> provider = getProvider(providerKey);
            return (T) provider;
        }

        if (!ignoreExplicitBindings) {
            Provider<T> provider = getProvider(key);
            if (provider != null) {
                return provider.get();
            }
        }

        ConstructorInjector<T> constructorInjector = membersInjectorFactory.getConstructorInjector(type);
        T instance = constructorInjector.createInstance();

        if (instance != null) {
            injectMembers(type, instance);
        }

        return instance;
    }

    @Override
    public Injector createChildInjector(Iterable<Module> modules) {

        InternalBinder privateBinder = new PrivateInternalBinder(binder);
        for (Module module : modules) {
            module.configure(privateBinder);
        }
        return new SimpleInjector(privateBinder, this);

    }

    public <T> Provider<T> getProvider(Key<T> key) {

        Binding<T> binding = binder.findBinding(key);

        if (binding == null) {
            return null;
        }

        if (!binding.isProviderInjected()) {
            Provider<T> provider = binding.getProvider();
            injectMembers(new Token<>(provider.getClass()), provider);
            binding.setProviderInjected(true);
            binder.setBinding(binding);
        }

        return binding.getProvider();

    }

}
