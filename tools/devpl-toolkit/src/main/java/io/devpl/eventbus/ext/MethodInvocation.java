package io.devpl.eventbus.ext;

import java.lang.reflect.Method;

public interface MethodInvocation {

    /**
     * Get the method being called.
     *
     * @return the method being called
     */
    Method getMethod();
}
