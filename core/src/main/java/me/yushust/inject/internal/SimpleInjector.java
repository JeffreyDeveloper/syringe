package me.yushust.inject.internal;

import me.yushust.inject.Injector;
import me.yushust.inject.Provider;
import me.yushust.inject.identity.type.TypeReference;
import me.yushust.inject.internal.injector.ConstructorInjector;
import me.yushust.inject.process.BindingAnnotationProcessor;
import me.yushust.inject.process.ProcessorInterceptor;
import me.yushust.inject.bind.Binding;
import me.yushust.inject.exception.UnsupportedInjectionException;
import me.yushust.inject.identity.Key;
import me.yushust.inject.bind.Module;
import me.yushust.inject.resolve.resolver.InjectableConstructorResolver;
import me.yushust.inject.resolve.resolver.InjectableMembersResolver;
import me.yushust.inject.util.Providers;

import java.lang.reflect.Member;
import java.util.Stack;

import static me.yushust.inject.internal.Preconditions.checkNotNull;

public class SimpleInjector implements InternalInjector {

    private final InternalBinder binder;
    private final InjectableConstructorResolver injectableConstructorResolver;
    private final MembersInjectorFactory membersInjectorFactory;
    private final BindingAnnotationProcessor bindingAnnotationProcessor;

    public SimpleInjector(InternalBinder binder, InjectableConstructorResolver injectableConstructorResolver,
                          InjectableMembersResolver membersResolver, ProcessorInterceptor processorInterceptor,
                          BindingAnnotationProcessor bindingAnnotationProcessor) {
        this.binder = checkNotNull(binder);
        this.injectableConstructorResolver = checkNotNull(injectableConstructorResolver);
        this.membersInjectorFactory = processorInterceptor.interceptMembersInjectorFactory(new SimpleMembersInjectorFactory(
                injectableConstructorResolver, membersResolver, this
        ));
        this.bindingAnnotationProcessor = bindingAnnotationProcessor;
    }

    private SimpleInjector(InternalBinder binder, SimpleInjector prototype) {
        this.binder = binder;
        this.injectableConstructorResolver = prototype.injectableConstructorResolver;
        this.membersInjectorFactory = prototype.membersInjectorFactory.usingInjector(this);
        this.bindingAnnotationProcessor = prototype.bindingAnnotationProcessor;
    }

    @Override
    public void injectMembers(Object object) {
        checkNotNull(object);
        injectMembers(TypeReference.of(object.getClass()), object);
    }


    @Override
    public <T> void injectMembers(TypeReference<T> typeReference, T instance) {
        injectMembers(typeReference, instance, new Stack<>());
    }

    @Override
    public <T> void injectMembers(TypeReference<T> typeReference, T instance, Stack<Member> injectionStack) {
        checkNotNull(typeReference);
        checkNotNull(instance);

        MembersInjector injector = membersInjectorFactory.getMembersInjector(typeReference);

        try {
            injector.injectMembers(instance, injectionStack);
        } catch (UnsupportedInjectionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> T getInstance(Class<T> type) {
        return getInstance(TypeReference.of(type));
    }

    @Override
    public <T> T getInstance(TypeReference<T> typeReference) {
        return getInstance(Key.of(typeReference));
    }

    @Override
    public <T> T getInstance(Key<T> key) {
        return getInstance(key, false);
    }

    @Override
    public <T> T getInstance(Key<T> key, boolean ignoreExplicitBindings) {
        return getInstance(key, ignoreExplicitBindings, new Stack<>());
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> T getInstance(Key<T> key, boolean ignoreExplicitBindings, Stack<Member> injectionStack) {
        checkNotNull(key);
        TypeReference<T> type = key.getType();

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

            if (type.getRawType() == type.getType()) { // it's not a generic type
                boolean bound = bindingAnnotationProcessor.bind(binder, type.getRawType());
                if (bound) {
                    return (T) getInstance(Key.of(type.getRawType()), false, injectionStack);
                }
            }

            if (provider != null) {
                return provider.get();
            }
        }

        ConstructorInjector<T> constructorInjector = membersInjectorFactory.getConstructorInjector(type);
        T instance = constructorInjector.createInstance();

        if (instance != null) {
            injectMembers(type, instance, injectionStack);
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
            injectMembers(TypeReference.of(provider.getClass()), provider);
            binding.setProviderInjected(true);
            binder.setBinding(binding);
        }

        return binding.getProvider();

    }

}
