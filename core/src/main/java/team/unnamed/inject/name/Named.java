package team.unnamed.inject.name;

import team.unnamed.inject.BindingAnnotation;

import javax.inject.Qualifier;

import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ CONSTRUCTOR, FIELD, PARAMETER, METHOD })
@BindingAnnotation
@Qualifier
public @interface Named {

    String value();

}
