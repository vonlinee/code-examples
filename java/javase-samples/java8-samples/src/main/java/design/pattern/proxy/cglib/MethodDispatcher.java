package design.pattern.proxy.cglib;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class MethodDispatcher implements MethodInterceptor {

	private Object realObject;
	private Map<Method, MethodInterceptor> interceptors = new HashMap<>();
	
	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		MethodInterceptor interceptor = interceptors.get(method);
		if (interceptor != null) {
			return interceptor.intercept(realObject, method, args, proxy);
		}
		return method.invoke(obj, args);
	}
}