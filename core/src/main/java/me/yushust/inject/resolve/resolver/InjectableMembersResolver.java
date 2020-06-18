package me.yushust.inject.resolve.resolver;

import me.yushust.inject.identity.token.Token;
import me.yushust.inject.resolve.InjectableMember;

import java.lang.reflect.Constructor;
import java.util.Set;

public interface InjectableMembersResolver {

    Set<InjectableMember> resolveInjectableMembers(Token<?> token);

    <T> InjectableMember transformConstructor(Token<T> declaringClass, Constructor<T> constructor);

}
