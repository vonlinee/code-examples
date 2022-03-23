package sample.spring.aop.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * MethodInterceptor extends Callback
 */
public class TargetInterceptor implements MethodInterceptor {
	
	@Override
	public Object intercept(Object obj, Method method, Object[] params, MethodProxy proxy) throws Throwable {
		System.out.println("调用前");
		Object result = proxy.invokeSuper(obj, params);
		
		Object returnValue1 = proxy.invoke(obj, params);
		
		System.out.println("调用后，返回值" + result);
		return result;
	}
}