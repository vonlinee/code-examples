package design.pattern.proxy.cglib;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;

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
        // 代理对象 design.pattern.proxy.cglib.TargetObject$$EnhancerByCGLIB$$651090fd
        Object proxy = enhancer.create();

        System.out.println("===========================================");
        Class<? extends Object> clazz = proxy.getClass();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
			System.out.println(method.getName());
		}
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
			System.out.println(field.getName());
		}
        
        
        System.out.println(proxy instanceof Enhancer);
        System.out.println(proxy instanceof TargetObject);
        
        Class<?> superclass = clazz.getSuperclass();
        System.out.println(superclass);
     
        Class<?>[] interfaces = clazz.getInterfaces();
        for (Class<?> interfaceItem : interfaces) {
			System.out.println(interfaceItem.getName()); // net.sf.cglib.proxy.Factory
		}
        
        Type genericSuperclass = clazz.getGenericSuperclass();
        System.out.println(genericSuperclass);
        
        System.out.println("===========================================");
        
        TargetObject targetObject2 = (TargetObject) proxy;


        System.out.println(targetObject2);
        System.out.println(targetObject2.method1("xxx"));
        System.out.println(targetObject2.method2(100));
        System.out.println(targetObject2.method3(200));
    }
}