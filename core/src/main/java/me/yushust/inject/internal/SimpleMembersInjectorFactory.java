package me.yushust.inject.internal;

import me.yushust.inject.identity.type.TypeReference;
import me.yushust.inject.internal.injector.ConstructorInjector;
import me.yushust.inject.internal.injector.FieldsInjector;
import me.yushust.inject.internal.injector.MethodsInjector;
import me.yushust.inject.internal.injector.DummyConstructorInjector;
import me.yushust.inject.internal.injector.ReflectionConstructorInjector;
import me.yushust.inject.resolve.InjectableMember;
import me.yushust.inject.resolve.resolver.InjectableConstructorResolver;
import me.yushust.inject.resolve.resolver.InjectableMembersResolver;

import java.lang.reflect.Constructor;
import java.util.Set;

import static me.yushust.inject.internal.Preconditions.checkNotNull;

public class SimpleMembersInjectorFactory implements MembersInjectorFactory {

    private final InjectableConstructorResolver constructorResolver;
    private final InjectableMembersResolver membersResolver;
    private final InternalInjector injector;

    public SimpleMembersInjectorFactory(InjectableConstructorResolver constructorResolver,
                                        InjectableMembersResolver membersResolver, InternalInjector injector) {
        this.constructorResolver = checkNotNull(constructorResolver);
        this.membersResolver = checkNotNull(membersResolver);
        this.injector = checkNotNull(injector);
    }

    @Override
    public MembersInjector getMembersInjector(TypeReference<?> key) {

        Set<InjectableMember> fields = membersResolver.resolveInjectableFields(key);
        Set<InjectableMember> methods = membersResolver.resolveInjectableMethods(key);

        MembersInjector fieldsInjector = new FieldsInjector(injector, key, fields);
        MembersInjector methodsInjector = new MethodsInjector(injector, key, methods);

        return new DelegatingMembersInjector(fieldsInjector, methodsInjector);

    }

    @Override
    public <T> ConstructorInjector<T> getConstructorInjector(TypeReference<T> key) {
        InjectableMember constructorMember;

        Constructor<T> constructor = constructorResolver.findInjectableConstructor(key);

        if (constructor == null) {
            // returns a constructor injector that doesn't
            // create instances
            return new DummyConstructorInjector<>(); // so, there're no injectable constructors
        }

        constructorMember = membersResolver.transformConstructor(key, constructor);

        return new ReflectionConstructorInjector<>(key, injector, constructorMember);
    }

    @Override
    public MembersInjectorFactory usingInjector(InternalInjector injector) {
        return new SimpleMembersInjectorFactory(constructorResolver, membersResolver, injector);
    }

}
