package me.yushust.inject.resolve.resolver;

import me.yushust.inject.Inject;
import me.yushust.inject.Resolve;
import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.type.TypeReference;
import me.yushust.inject.resolve.InjectableMember;
import me.yushust.inject.resolve.OptionalInjectionChecker;
import me.yushust.inject.resolve.ResolvableKey;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

import static me.yushust.inject.internal.Preconditions.checkNotNull;

public class ReflectionInjectableMembersResolver implements InjectableMembersResolver {

    private final OptionalInjectionChecker optionalInjectionChecker;
    private final MemberKeyResolver memberKeyResolver;
    private final boolean requireResolveAnnotation;

    public ReflectionInjectableMembersResolver(OptionalInjectionChecker optionalInjectionChecker,
                                               MemberKeyResolver memberKeyResolver,
                                               boolean requireResolveAnnotation) {
        this.optionalInjectionChecker = checkNotNull(optionalInjectionChecker);
        this.memberKeyResolver = checkNotNull(memberKeyResolver);
        this.requireResolveAnnotation = requireResolveAnnotation;
    }

    @Override
    public Set<InjectableMember> resolveInjectableFields(TypeReference<?> type) {
        checkNotNull(type);

        Set<InjectableMember> members = new HashSet<>();

        Class<?> current = type.getRawType();
        Resolve resolveAnnotation = null;

        while (
                (!requireResolveAnnotation && current != Object.class) ||
                (resolveAnnotation = current.getAnnotation(Resolve.class)) != null
        ) {
            for (Field field : current.getDeclaredFields()) {
                if (field.getAnnotation(Inject.class) == null
                        && field.getAnnotation(javax.inject.Inject.class) == null) {
                    continue;
                }
                Key<?> key = memberKeyResolver.keyOf(type, field);
                boolean optional = optionalInjectionChecker.isFieldOptional(field);
                field.setAccessible(true);
                members.add(new InjectableMember(type, field, Collections.singletonList(
                        new ResolvableKey<>(key, optional)
                )));
            }
            if (resolveAnnotation != null && !resolveAnnotation.checkParentClasses()) {
                break;
            }
            current = current.getSuperclass();
        }

        return members;
    }

    @Override
    public Set<InjectableMember> resolveInjectableMethods(TypeReference<?> type) {
        checkNotNull(type);
        Set<InjectableMember> members = new HashSet<>();

        Class<?> current = type.getRawType();

        while (current != Object.class) {
            for (Method method : current.getDeclaredMethods()) {
                if (method.getAnnotation(Inject.class) == null
                        && method.getAnnotation(javax.inject.Inject.class) == null) {
                    continue;
                }
                method.setAccessible(true);
                members.add(new InjectableMember(type, method, resolveKeys(type, method.getParameters())));
            }
            current = current.getSuperclass();
        }

        return members;
    }

    @Override
    public <T> InjectableMember transformConstructor(TypeReference<T> declaringClass, Constructor<T> constructor) {
        checkNotNull(declaringClass);
        checkNotNull(constructor);
        return new InjectableMember(declaringClass, constructor, resolveKeys(declaringClass, constructor.getParameters()));
    }

    private List<ResolvableKey<?>> resolveKeys(TypeReference<?> declaringClass, Parameter[] parameters) {
        List<ResolvableKey<?>> keys = new LinkedList<>();

        for (Parameter parameter : parameters) {
            Key<?> key = memberKeyResolver.keyOf(declaringClass, parameter);
            boolean optional = optionalInjectionChecker.isParameterOptional(parameter);
            keys.add(new ResolvableKey<>(key, optional));
        }

        return keys;
    }

}
