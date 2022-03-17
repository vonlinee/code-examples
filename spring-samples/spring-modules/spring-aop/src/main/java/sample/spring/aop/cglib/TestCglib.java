package sample.spring.aop.cglib;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InterfaceMaker;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.NoOp;

/**
 * aopalliance: aop + alliance(联盟)
 * 基本介绍：https://blog.csdn.net/sun_tantan/article/details/107497476
 * 
 * Spring AOP、AspectJ、CGLIB 都是什么鬼？它们有什么关系 https://zhuanlan.zhihu.com/p/426442535
 * 
 * 要想Spring AOP 通过CGLIB生成代理，只需要在Spring 的配置文件引入
 * <aop:aspectj-autoproxy proxy-target-class="true"/>
 * 
 * 
 * AspectJ 和 Spring AOP 在实现上几乎无关
 * 
 * 
 * 深入理解InnoDB -- 存储篇: https://zhuanlan.zhihu.com/p/161737133
 *
 */
public class TestCglib {

	public static void main(String[] args) {
		test1();
	}

	public static void test1() {
		// 设置输出代理类到指定路径，便于后面分析
		System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "");
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(TargetObject.class); // 设置哪个类需要代理
		Callback callback = new TargetInterceptor();
		enhancer.setCallback(callback); // 设置怎么代理
		TargetObject proxy = (TargetObject) enhancer.create(); // 获取代理类实例
		String returnValue = proxy.method("zs");
		System.out.println("returnValue = " + returnValue);
	}

	public static void test2() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(TargetObject.class);
		CallbackFilter callbackFilter = new TargetMethodCallbackFilter();
		Callback noopCb = NoOp.INSTANCE; // 表示什么操作也不做，代理类直接调用被代理的方法不进行拦截。
		Callback callback1 = new TargetInterceptor(); // 方法拦截器
		Callback fixedValue = new TargetResultFixed(); // 锁定方法返回值
		Callback[] cbarray = new Callback[] { callback1, noopCb, fixedValue };
		enhancer.setCallbacks(cbarray);
		enhancer.setCallbackFilter(callbackFilter);
		TargetObject targetObject2 = (TargetObject) enhancer.create();
	}

	public static void test3() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		InterfaceMaker interfaceMaker = new InterfaceMaker();
		interfaceMaker.add(TargetObject.class); // 抽取哪个类的方法
		Class<?> targetInterface = interfaceMaker.create();
		for (Method method : targetInterface.getMethods()) {
			System.out.println(method.getName());
		}
		// 接口代理并设置代理接口方法拦截
		Object object = Enhancer.create(Object.class, new Class[] { targetInterface }, new MethodInterceptor() {
			@Override
			public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy)
					throws Throwable {
				return "default";
			}
		});
		Method targetMethod1 = object.getClass().getMethod("method3", int.class);
		int i = (int) targetMethod1.invoke(object, new Object[] { 33 });
		Method targetMethod = object.getClass().getMethod("method1", String.class);
		System.out.println(targetMethod.invoke(object, "sdfs"));
	}
}
