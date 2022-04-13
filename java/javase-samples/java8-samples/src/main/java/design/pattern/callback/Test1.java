package design.pattern.callback;

import java.lang.reflect.Method;

public class Test1 {
	public static void main(String[] args) throws Exception {
		Test1 test = new Test1();
		Method callBackMethod = Test1.class.getMethod("callback");
		test.addCallBack(callBackMethod);
	}
	
	public int callback() {
		System.out.println("invoke callback method!");
		return 1;
	}
	
	public void addCallBack(Method method) throws Exception {
		Object returnValue = method.invoke(this);
		System.out.println(returnValue);
	}
}