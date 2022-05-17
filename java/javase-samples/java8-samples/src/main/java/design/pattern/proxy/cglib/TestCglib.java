package design.pattern.proxy.cglib;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;

import java.io.File;

public class TestCglib {

    static {
        String absolutePath = new File("").getAbsolutePath();
        // 保存CGLIB生成的类
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D://");
    }

    public static void main(String args[]) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(TargetObject.class);

        // enhancer.setCallbacks(null); 同时设置多个
        enhancer.setCallback(new TargetInterceptor());

        // 调试看不出
        Object proxy = enhancer.create();

        TargetObject targetObject2 = (TargetObject) proxy;



        System.out.println(targetObject2);
        System.out.println(targetObject2.method1("mmm1"));
        System.out.println(targetObject2.method2(100));
        System.out.println(targetObject2.method3(200));
    }
}