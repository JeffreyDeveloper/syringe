package me.yushust.inject.internal.injector;

import me.yushust.inject.Inject;
import me.yushust.inject.Injector;
import me.yushust.inject.exception.UnsupportedInjectionException;
import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.token.Token;
import me.yushust.inject.internal.MembersInjector;
import me.yushust.inject.resolvable.AnnotationTypeHandler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FieldsInjector implements MembersInjector {

    private final AnnotationTypeHandler annotationTypeHandler;
    private final Injector injector;

    private final Token<?> declaringClass;
    private final List<InjectableField> injections;

    public FieldsInjector(AnnotationTypeHandler annotationTypeHandler, Injector injector, Token<?> declaringClass) {
        this.annotationTypeHandler = Objects.requireNonNull(annotationTypeHandler);
        this.injector = Objects.requireNonNull(injector);
        this.declaringClass = Objects.requireNonNull(declaringClass);
        this.injections = this.getInjections();
    }

    @Override
    public void injectMembers(Object instance) throws UnsupportedInjectionException {

        for (InjectableField injection : injections) {

            Key<?> key = injection.key;
            Object injectedValue = injector.getInstance(key);
            Field field = injection.field;

            if (injectedValue == null && !injection.optional) {
                throw new UnsupportedInjectionException(
                        "Cannot create an instance for key " + key.toString() + " of class " + declaringClass.toString()
                );
            }

            try {
                field.set(instance, injectedValue);
            } catch (IllegalAccessException e) {
                throw new UnsupportedInjectionException(
                        "Cannot access to field " + field.getName() + " of class " + declaringClass.toString()
                );
            }

        }

    }

    private List<InjectableField> getInjections() {

        List<InjectableField> injections = new ArrayList<>();

        for (Field field : declaringClass.getRawType().getDeclaredFields()) {

            Inject spec = field.getAnnotation(Inject.class);

            if (spec == null) {
                continue;
            }

            field.setAccessible(true);
            boolean optional = spec.optional();
            Key<?> key = annotationTypeHandler.keyOfField(field);
            injections.add(new InjectableField(key, field, optional));

        }

        return injections;

    }

    public static class InjectableField {

        private final Key<?> key;
        private final Field field;
        private final boolean optional;

        public InjectableField(Key<?> key, Field field, boolean optional) {
            this.key = key;
            this.field = field;
            this.optional = optional;
        }

    }

}
