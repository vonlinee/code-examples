package design.pattern.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class InvocationHandlerImpl implements InvocationHandler {

    Object realObj;

    public InvocationHandlerImpl(Object target) {
        this.realObj = target;
    }

    /**
     * 代理对象的所有方法调用都会进到此方法，通过反射调用，因此需要持有被代理对象的实例
     * ，这就和静态代理一样了
     * @param proxy  代理对象实例
     * @param method 方法
     * @param args   方法参数
     * @return method.invoke()
     * @throws Throwable InvokeException
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 这里不能使用proxy作为实际调用者，如果不做处理的话会导致递归栈溢出
        // 如果是调用继承自Object类的方法，则直接执行即可
        if (Object.class.equals(method.getDeclaringClass())) {
            System.out.println(method);
            return method.invoke(realObj);
        }
        return method.invoke(realObj, args);
    }
}