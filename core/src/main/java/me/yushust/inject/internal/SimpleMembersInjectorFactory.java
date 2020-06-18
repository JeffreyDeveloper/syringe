package me.yushust.inject.internal;

import me.yushust.inject.Injector;
import me.yushust.inject.identity.token.Token;
import me.yushust.inject.internal.injector.*;
import me.yushust.inject.resolve.InjectableMember;
import me.yushust.inject.resolve.resolver.InjectableConstructorResolver;
import me.yushust.inject.resolve.resolver.InjectableMembersResolver;

import java.lang.reflect.Constructor;
import java.util.Set;

import static me.yushust.inject.internal.Preconditions.checkNotNull;

public class SimpleMembersInjectorFactory implements MembersInjectorFactory {

    private final InjectableConstructorResolver constructorResolver;
    private final InjectableMembersResolver membersResolver;
    private final Injector injector;

    public SimpleMembersInjectorFactory(InjectableConstructorResolver constructorResolver,
                                        InjectableMembersResolver membersResolver, Injector injector) {
        this.constructorResolver = checkNotNull(constructorResolver);
        this.membersResolver = checkNotNull(membersResolver);
        this.injector = checkNotNull(injector);
    }

    @Override
    public MembersInjector getMembersInjector(Token<?> key) {

        Set<InjectableMember> members = membersResolver.resolveInjectableMembers(key);

        MembersInjector fieldsInjector = new FieldsInjector(injector, key, members);
        MembersInjector methodsInjector = new MethodsInjector(injector, key, members);

        return new DelegatingMembersInjector(fieldsInjector, methodsInjector);

    }

    @Override
    public <T> ConstructorInjector<T> getConstructorInjector(Token<T> key) {
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

}
