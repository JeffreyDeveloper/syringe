package me.yushust.inject.exception;

import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.token.Token;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class ExceptionFactory {

    private ExceptionFactory() {
        throw new UnsupportedOperationException("This class couldn't be instantiated!");
    }

    public static InjectionException cannotInjectConstructor(Token<?> declaringClass, Key<?> injectingKey, String cause) {
        return new InjectionException(
                "Cannot inject dependencies into constructor of " + declaringClass.toString() +" class." +
                        " Cause: " + cause +
                        " Key: " + injectingKey.toString()
        );
    }

    public static InjectionException cannotInstantiateClass(Token<?> clazz, Throwable cause) {
        return new InjectionException(
                "Cannot instantiate class " + clazz.toString(),
                cause
        );
    }

    public static UnsupportedInjectionException cannotInjectMethod(Token<?> declaringClass, Key<?> injectingKey, Method method) {
        return new UnsupportedInjectionException(
                "Cannot inject dependencies into method of " + declaringClass.toString() + " class." +
                        " Key: " + injectingKey.toString() +
                        " Method: " + method.getName()
        );
    }

    public static InjectionException cannotInvokeInjectableMethod(Token<?> declaringClass, Method method, Throwable cause) {
        return new InjectionException(
                "Cannot invoke injectable method of class " + declaringClass.toString() + " class." +
                        " Method: " + method.getName(),
                cause
        );
    }

    public static InjectionException cannotSetFieldValue(Token<?> declaringClass, Field field, Throwable cause) {
        return new InjectionException(
                "Cannot set field value of class " + declaringClass.toString() + " class." +
                        "Field: " + field.getName(),
                cause
        );
    }

    public static UnsupportedInjectionException cannotInjectField(Token<?> declaringClass, Key<?> injectingKey, Field field) {
        return new UnsupportedInjectionException(
                "Cannot inject dependencies into field of " + declaringClass.toString() + " class." +
                        " Key: " + injectingKey.toString() +
                        " Field: " + field.getName()
        );
    }


    public static InvalidBindingException cannotExposeBinding(Key<?> key, String cause) {
        return new InvalidBindingException(
                "Cannot expose binding of key " + key.toString() + "." +
                        " Cause: " + cause
        );
    }

}
