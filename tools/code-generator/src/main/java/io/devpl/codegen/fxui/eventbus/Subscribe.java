package io.devpl.codegen.fxui.eventbus;

import io.devpl.codegen.fxui.framework.EventBus;

import java.lang.annotation.*;

/**
 * Marks a method as an event subscriber.
 *
 * <p>The type of event will be indicated by the method's first (and only) parameter, which cannot
 * be primitive. If this annotation is applied to methods with zero parameters, or more than one
 * parameter, the object containing the method will not be able to register for event delivery from
 * the {@link EventBus}.
 *
 * <p>Unless also annotated with @{AllowConcurrentEvents}, event subscriber methods will be
 * invoked serially by each event bus that they are registered with.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Subscribe {

}