package org.example.springboot.aop.cglib;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;


public class LogInterceptor implements MethodInterceptor {
//    // 这里传入的obj是代理类对象，而不是目标类对象
//    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
//        System.err.println("打印" + method.getName() + "方法的入参");
//        // 注意，这里要调用proxy.invokeSuper，而不是method.invoke，不然会出现栈溢出等问题
//        Object obj2 = proxy.invokeSuper(obj, args);
//        System.err.println("打印" + method.getName() + "方法的出参");
//        return obj2;
//    }

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		return null;
	}
}