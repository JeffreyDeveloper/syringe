package me.yushust.inject.resolve.resolver;

import me.yushust.inject.Inject;
import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.token.Token;
import me.yushust.inject.resolve.InjectableMember;
import me.yushust.inject.resolve.OptionalInjectionChecker;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

import static me.yushust.inject.internal.Preconditions.checkNotNull;

public class ReflectionInjectableMembersResolver implements InjectableMembersResolver {

    private final OptionalInjectionChecker optionalInjectionChecker;
    private final MemberKeyResolver memberKeyResolver;

    public ReflectionInjectableMembersResolver(OptionalInjectionChecker optionalInjectionChecker,
                                               MemberKeyResolver memberKeyResolver) {
        this.optionalInjectionChecker = checkNotNull(optionalInjectionChecker);
        this.memberKeyResolver = checkNotNull(memberKeyResolver);
    }

    @Override
    public Set<InjectableMember> resolveInjectableMembers(Token<?> token) {
        checkNotNull(token);

        Set<InjectableMember> members = new HashSet<>();

        // resolve fields
        for (Field field : token.getRawType().getDeclaredFields()) {
            if (field.getAnnotation(Inject.class) == null
                && field.getAnnotation(javax.inject.Inject.class) == null) {
                continue;
            }
            Key<?> key = memberKeyResolver.keyOf(field);
            boolean optional = optionalInjectionChecker.isFieldOptional(field);
            field.setAccessible(true);
            members.add(new InjectableMember(token, field, Collections.singletonList(
                    new InjectableMember.KeyEntry<>(key, optional)
            )));
        }

        // resolve methods
        for (Method method : token.getRawType().getDeclaredMethods()) {
            if (method.getAnnotation(Inject.class) == null
                    && method.getAnnotation(javax.inject.Inject.class) == null) {
                continue;
            }
            method.setAccessible(true);
            members.add(new InjectableMember(token, method, resolveKeys(method.getParameters())));
        }

        return members;
    }

    @Override
    public <T> InjectableMember transformConstructor(Token<T> declaringClass, Constructor<T> constructor) {
        checkNotNull(declaringClass);
        checkNotNull(constructor);
        return new InjectableMember(declaringClass, constructor, resolveKeys(constructor.getParameters()));
    }

    private List<InjectableMember.KeyEntry<?>> resolveKeys(Parameter[] parameters) {
        List<InjectableMember.KeyEntry<?>> keys = new LinkedList<>();

        for (Parameter parameter : parameters) {
            Key<?> key = memberKeyResolver.keyOf(parameter);
            boolean optional = optionalInjectionChecker.isParameterOptional(parameter);
            keys.add(new InjectableMember.KeyEntry<>(key, optional));
        }

        return keys;
    }

}
