package design.pattern.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Test1 {

	static {
        // 保存生成的代理类
		System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
	}

	public static void main(String[] args) {
		test3();
	}

	public static void test1() {
		// 实际调用方法的对象
		TargetInterface targetObj = param -> "被代理对象";
		InvocationHandlerImpl h = new InvocationHandlerImpl(targetObj);
		ClassLoader classLoader = TargetInterface.class.getClassLoader();
		Class<?>[] interfaces = {
				TargetInterface.class
		};
		Object proxyInstance = Proxy.newProxyInstance(classLoader, interfaces, h);
		// JDK的动态代理本质上基于接口生成代理类的对象，此对象继承自该接口
		if (proxyInstance instanceof TargetInterface) {
			TargetInterface proxy = (TargetInterface) proxyInstance;
			String val = proxy.targetMethod("paramValue");
			System.out.println(val);
		}
		System.out.println(proxyInstance.equals(1));
	}

	public static void test2() {
//		InvocationHandler handler = new ProxyMethodDispatcher<>(TargetInterface.class);
		InvocationHandler handler = null;
		ClassLoader classLoader = TargetInterface.class.getClassLoader();
		Class<?>[] interfaces = {
				TargetInterface.class
		};
		Object proxyInstance = Proxy.newProxyInstance(classLoader, interfaces, handler);

		// getClass方法不会走代理 com.sun.proxy.$Proxy0
		String name = proxyInstance.getClass().getName();
		
		if (proxyInstance instanceof Proxy) {
			System.out.println("1111111111111");
		}
		System.out.println(name);
	}
	
	public static void test3() {
		TargetInterface targetInterface = Proxys.newProxyInstance(TargetInterface.class);
		System.out.println(targetInterface.getClass());
	}
}
