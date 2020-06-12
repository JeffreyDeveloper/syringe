package me.yushust.inject.internal;

import me.yushust.inject.Injector;
import me.yushust.inject.identity.token.Token;
import me.yushust.inject.internal.injector.FieldsInjector;
import me.yushust.inject.internal.injector.MethodsInjector;
import me.yushust.inject.resolvable.AnnotationTypeHandler;
import me.yushust.inject.resolvable.ParameterKeysResolver;

import java.util.Objects;

public class SimpleMembersInjectorFactory implements MembersInjectorFactory {

    private final AnnotationTypeHandler annotationTypeHandler;
    private final ParameterKeysResolver parameterKeysResolver;
    private final Injector injector;

    public SimpleMembersInjectorFactory(AnnotationTypeHandler annotationTypeHandler,
                                        ParameterKeysResolver parameterKeysResolver, Injector injector) {
        this.annotationTypeHandler = Objects.requireNonNull(annotationTypeHandler);
        this.parameterKeysResolver = Objects.requireNonNull(parameterKeysResolver);
        this.injector = Objects.requireNonNull(injector);
    }

    @Override
    public MembersInjector getMembersInjector(Token<?> key) {

        MembersInjector fieldsInjector = new FieldsInjector(annotationTypeHandler, injector, key);
        MembersInjector methodsInjector = new MethodsInjector(parameterKeysResolver, injector, key);

        return new DelegatingMembersInjector(fieldsInjector, methodsInjector);

    }

}
