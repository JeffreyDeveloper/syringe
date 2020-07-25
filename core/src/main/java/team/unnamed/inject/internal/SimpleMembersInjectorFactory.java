package team.unnamed.inject.internal;

import team.unnamed.inject.identity.type.TypeReference;
import team.unnamed.inject.internal.injector.ConstructorInjector;
import team.unnamed.inject.internal.injector.FieldsInjector;
import team.unnamed.inject.internal.injector.MethodsInjector;
import team.unnamed.inject.internal.injector.DummyConstructorInjector;
import team.unnamed.inject.internal.injector.ReflectionConstructorInjector;
import team.unnamed.inject.resolve.InjectableMember;
import team.unnamed.inject.resolve.resolver.InjectableConstructorResolver;
import team.unnamed.inject.resolve.resolver.InjectableMembersResolver;

import java.lang.reflect.Constructor;
import java.util.Set;

public class SimpleMembersInjectorFactory implements MembersInjectorFactory {

    private final InjectableConstructorResolver constructorResolver;
    private final InjectableMembersResolver membersResolver;
    private final InternalInjector injector;

    public SimpleMembersInjectorFactory(InjectableConstructorResolver constructorResolver,
                                        InjectableMembersResolver membersResolver, InternalInjector injector) {
        this.constructorResolver = Preconditions.checkNotNull(constructorResolver);
        this.membersResolver = Preconditions.checkNotNull(membersResolver);
        this.injector = Preconditions.checkNotNull(injector);
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
