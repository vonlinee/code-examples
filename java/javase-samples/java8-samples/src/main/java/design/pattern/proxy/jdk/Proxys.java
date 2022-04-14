package design.pattern.proxy.jdk;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;

public final class Proxys {

	@SuppressWarnings("unchecked")
    public static <T> T newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h, Class<T> clazz, boolean skipCheck) {
    	Object proxyInstance = Proxy.newProxyInstance(loader, interfaces, h);
    	// 类型转换检查
    	if (!skipCheck) {
        	if (clazz == Proxy.class) {
    			return (T) proxyInstance;
    		}
        	boolean flag = false;
        	for (int i = 0; i < interfaces.length; i++) {
        		if (clazz == interfaces[i]) {
        			flag = true;
    				break;
    			}
    		}
        	if (flag) {
        		return (T) proxyInstance;
			} 
        	throw new UnsupportedOperationException(String.format("[%s] => [%s]", clazz, interfaces));
		}
    	return (T) proxyInstance;
    }
    
    public static <T> T newProxyInstance(Class<T> targetInterface, InvocationHandler h, Class<T> clazz) {
		return newProxyInstance(targetInterface.getClassLoader(), new Class<?>[] {targetInterface}, h, clazz, false);
    }
    
    public static <T> T newProxyInstance(Class<T> targetInterface, Class<T> clazz) {
		return newProxyInstance(targetInterface.getClassLoader(), new Class<?>[] {targetInterface}, new ProxyMethodDispatcher<>(targetInterface), clazz, false);
    }
    
    public static <T> T newProxyInstance(Class<T> targetInterface) {
		return newProxyInstance(targetInterface.getClassLoader(), new Class<?>[] {targetInterface}, new ProxyMethodDispatcher<>(targetInterface), targetInterface, false);
    }
}
