package sample.spring.aop.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.CallbackFilter;

public class TargetMethodCallbackFilter implements CallbackFilter {
	@Override
	public int accept(Method method) {
		if (method.getName().equals("method1")) {
			System.out.println("filter method1 == 0");
			return 0;
		}
		return 0;
	}
}