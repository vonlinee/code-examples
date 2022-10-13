package io.devpl.eventbus.ext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectiveMethodInvocation implements MethodInvocation {

    private final Object target;
    private final Method method;
    private final Object[] args;

    public ReflectiveMethodInvocation(Object target, Method method, Object[] args) {
        this.target = target;
        this.method = method;
        this.args = args;
    }

    public Object getTarget() {
        return target;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    public Object[] getArgs() {
        return args;
    }

    public Object proceed() throws InvocationTargetException, IllegalAccessException {
        return method.invoke(target, args);
    }
}
