package design.pattern.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 对InvocationHandler进行代理
 */
public class ProxyInvocationHandler implements InvocationHandler {

	private InvocationHandler handler;
	
	public ProxyInvocationHandler(InvocationHandler handler) {
		if (handler.getClass().isInterface()) {
			throw new UnsupportedOperationException();
		}
		this.handler = handler;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if ("invoke".equals(method.getName())) {
			return method.invoke(handler, args);
		} else {
			throw new InvocationTargetException(new UnsupportedOperationException());
		}
	}
}
