package me.yushust.inject;

import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ FIELD, CONSTRUCTOR, METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Inject {

    boolean optional() default false;

}
