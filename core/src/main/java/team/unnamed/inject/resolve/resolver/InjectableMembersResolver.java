package team.unnamed.inject.resolve.resolver;

import team.unnamed.inject.identity.type.TypeReference;
import team.unnamed.inject.resolve.InjectableMember;

import java.lang.reflect.Constructor;
import java.util.Set;

public interface InjectableMembersResolver {

    Set<InjectableMember> resolveInjectableFields(TypeReference<?> typeReference);

    Set<InjectableMember> resolveInjectableMethods(TypeReference<?> typeReference);

    <T> InjectableMember transformConstructor(TypeReference<T> declaringClass, Constructor<T> constructor);

}
