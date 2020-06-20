package me.yushust.inject.resolve.resolver;

import me.yushust.inject.resolve.InjectableMember;

import java.lang.reflect.Constructor;
import java.util.Set;

public interface InjectableMembersResolver {

    Set<InjectableMember> resolveInjectableFields(Class<?> token);

    Set<InjectableMember> resolveInjectableMethods(Class<?> token);

    <T> InjectableMember transformConstructor(Class<T> declaringClass, Constructor<T> constructor);

}
