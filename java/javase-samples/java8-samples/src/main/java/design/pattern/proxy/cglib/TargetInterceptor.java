package design.pattern.proxy.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 目标对象拦截器，实现MethodInterceptor
 */
public class TargetInterceptor implements MethodInterceptor {

    /**
	 * 重写方法拦截在方法前和方法后加入业务 Object obj为目标对象 Method method为目标方法 Object[] params 为参数，MethodProxy proxy CGlib方法代理对象
     * @param obj    CGLib动态生成的代理类实例
     * @param method 上文中实体类所调用的被代理的方法引用
     * @param params 参数值列表
     * @param proxy  生成的代理类对方法的代理引用
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] params, MethodProxy proxy) throws Throwable {
        System.out.println("调用前");
        Object result = proxy.invokeSuper(obj, params);
        System.out.println(" 调用后" + result);
        return result;
    }
}