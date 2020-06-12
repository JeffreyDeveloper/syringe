package me.yushust.inject.name;

import me.yushust.inject.LinkingAnnotation;

import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ CONSTRUCTOR, FIELD, PARAMETER, METHOD })
@LinkingAnnotation
public @interface Named {

    String value();

}
