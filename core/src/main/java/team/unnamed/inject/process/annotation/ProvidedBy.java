package team.unnamed.inject.process.annotation;

import team.unnamed.inject.Provider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProvidedBy {

    Class<? extends Provider<?>> value();

}
