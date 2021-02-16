package net.cg360.nsapi.commons.event.handler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface EventHandler {

    Priority getPriority() default Priority.NORMAL;
    boolean ignoreCancelled() default true;

}
