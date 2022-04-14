package design.pattern.proxy.cglib;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;

public class TestCglib {

    static {
        // 保存CGLIB生成的类
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D://");
    }

    public static void main(String args[]) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(TargetObject.class);
        enhancer.setCallback(new TargetInterceptor());
        TargetObject targetObject2 = (TargetObject) enhancer.create();
        System.out.println(targetObject2);
        System.out.println(targetObject2.method1("mmm1"));
        System.out.println(targetObject2.method2(100));
        System.out.println(targetObject2.method3(200));
    }
}