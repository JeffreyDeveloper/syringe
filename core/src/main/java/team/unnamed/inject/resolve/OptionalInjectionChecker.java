package team.unnamed.inject.resolve;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;

public interface OptionalInjectionChecker {

    boolean isParameterOptional(Parameter parameter);

    boolean isFieldOptional(Field field);

}
