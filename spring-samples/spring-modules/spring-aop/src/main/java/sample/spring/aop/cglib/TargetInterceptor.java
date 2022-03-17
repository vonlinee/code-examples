package sample.spring.aop.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class TargetInterceptor implements MethodInterceptor {
	@Override
	public Object intercept(Object obj, Method method, Object[] params, MethodProxy proxy) throws Throwable {
		System.out.println("调用前");
		Object result = proxy.invokeSuper(obj, params);
		System.out.println("调用后" + result);
		return result;
	}
}