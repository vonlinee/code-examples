package design.pattern.proxy.jdk;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 分发代理方法调用
 *
 * @param <T>
 */
public class ProxyMethodDispatcher<T> implements InvocationHandler {

    private Class<T> targetInterface; // 接口，并非实现类
    private Object realTarget;

    public ProxyMethodDispatcher(Class<T> targetInterface) {
        this.targetInterface = targetInterface;
    }

    public Object getRealTarget() {
        return realTarget;
    }

    public void setRealTarget(Object realTarget) {
        this.realTarget = realTarget;
    }

    /**
     * proxy: 代理类，继承了Proxy，实现了targetInterface，实际类型：ProxyMethodDispatcher
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return dispatch(proxy, method, args);
    }

    private Object dispatch(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            // 不能使用targetInterface，因为它是Class<T>，并且是接口，并非实现类
            // 实际上this就相当于代理类，和真实类是一类相同的对象，两者功能声明一致
            return method.invoke(this, args);
        }
        // 代理默认方法
        if (isDefaultMethod(method)) {
            return invokeDefaultMethod(proxy, method, args);
        }
        return null;
    }

    private boolean isDefaultMethod(Method method) {
        return (method.getModifiers() & (Modifier.ABSTRACT | Modifier.PUBLIC | Modifier.STATIC)) == Modifier.PUBLIC
                && method.getDeclaringClass().isInterface();
    }

    private static final int ALLOWED_MODES = MethodHandles.Lookup.PRIVATE | MethodHandles.Lookup.PROTECTED
            | MethodHandles.Lookup.PACKAGE | MethodHandles.Lookup.PUBLIC;

    private Object invokeDefaultMethod(Object proxy, Method method, Object[] args) throws Throwable {
        final Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class
                .getDeclaredConstructor(Class.class, int.class);
        if (!constructor.isAccessible()) {
            constructor.setAccessible(true);
        }
        final Class<?> declaringClass = method.getDeclaringClass();
        return constructor.newInstance(declaringClass, ALLOWED_MODES).unreflectSpecial(method, declaringClass)
                .bindTo(proxy).invokeWithArguments(args);
    }
}