package team.unnamed.inject.exception;

import team.unnamed.inject.identity.Key;
import team.unnamed.inject.identity.type.TypeReference;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Stack;

public final class ExceptionFactory {

    private ExceptionFactory() {
        throw new UnsupportedOperationException("This class couldn't be instantiated!");
    }

    public static InjectionException cannotInjectConstructor(TypeReference<?> declaringClass, Key<?> injectingKey, String cause) {
        return new InjectionException(
                "Cannot inject dependencies into constructor of " + declaringClass.toString() +" class." +
                        " Cause: " + cause +
                        " Key: " + injectingKey.toString()
        );
    }

    public static InjectionException cannotInstantiateClass(TypeReference<?> clazz, Throwable cause) {
        return new InjectionException(
                "Cannot instantiate class " + clazz.toString(),
                cause
        );
    }

    public static UnsupportedInjectionException cannotInjectMethod(TypeReference<?> declaringClass, Key<?> injectingKey, Method method) {
        return new UnsupportedInjectionException(
                "Cannot inject dependencies into method of " + declaringClass.toString() + " class." +
                        " Key: " + injectingKey.toString() +
                        " Method: " + method.getName()
        );
    }

    public static InjectionException cannotInvokeInjectableMethod(TypeReference<?> declaringClass, Method method, Throwable cause) {
        return new InjectionException(
                "Cannot invoke injectable method of class " + declaringClass.toString() + " class." +
                        " Method: " + method.getName(),
                cause
        );
    }

    public static InjectionException cannotSetFieldValue(TypeReference<?> declaringClass, Field field, Throwable cause) {
        return new InjectionException(
                "Cannot set field value of class " + declaringClass.toString() + " class." +
                        "Field: " + field.getName(),
                cause
        );
    }

    public static InjectionException cyclicInjectionDetected(Stack<Member> stack) {
        StringBuilder cycle = new StringBuilder();
        cycle.append(stack.firstElement().getDeclaringClass().getName());
        stack.remove(0);
        for (Member member : stack) {
            cycle.append(" -> ").append(member.getDeclaringClass().getName());
        }
        return new InjectionException(
                "Cyclic dependency detected! " + cycle.toString()
        );
    }

    public static UnsupportedInjectionException cannotInjectField(TypeReference<?> declaringClass, Key<?> injectingKey, Field field) {
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
