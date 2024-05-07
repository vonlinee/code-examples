package org.example.jvm.specialmethod;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class SignaturePolymorphic {

    public void method(int i) {
        System.out.println(i);
    }

    public static void main(String[] args) throws Throwable {
        // 以这种方式得到的lookup很强大，凡是调用类支持的字节码操作，它都支持
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        // 以此种方式创建的lookup能力是受限的，其只能访问类中public的成员
        MethodHandles.Lookup pubLookup = MethodHandles.publicLookup();

        // 第一个参数是方法的返回类型，第二参数是方法的入参
        // 其有很多非常方便的重载，基本满足了一般的使用场景
        MethodType methodType = MethodType.methodType(void.class, int.class);

        MethodHandle mh = lookup.findVirtual(SignaturePolymorphic.class, "method", methodType);

        Object val = mh.invoke(10);

        System.out.println(val);
    }
}
