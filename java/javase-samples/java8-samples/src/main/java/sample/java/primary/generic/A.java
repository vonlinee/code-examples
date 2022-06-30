package sample.java.primary.generic;

import java.util.List;

public class A<K, M, H> {

    public <T> void m1(T t) {
        if (t instanceof Object) {
            System.out.println("=====================");
        }
    }

    public static void main(String[] args) {
        //编译时无法确定getObject()返回的引用类型的具体类型，下面两句都能通过编译
        System.out.println(getObject() instanceof Object); //
        System.out.println(getObject() instanceof String);
        System.out.println(getObject() instanceof A);

        //编译时可以确定类型的，能够cast则编译通过，否则编译失败
        A a = new A();
        System.out.println(a instanceof A);//ok
        System.out.println(a instanceof Object); //ok
//        System.out.println(a instanceof String);//error,可以通过下面方法
        System.out.println((Object) a instanceof String);

        //跟泛型相关的注意一下
        //List是泛型类型，如果不指定泛型参数，成功编译
        System.out.println(a instanceof List);
        //如果不限定类型界限，通过编译
        // System.out.println(a instanceof List<Object>);

        //指定泛型参数，编译时可确定类型，如果不能cast，编译不通过
        // System.out.println(getObject() instanceof List<A>); //error
        // System.out.println(a instanceof List<A>);//error
    }

    public static Object getObject() {
        // Raw use of parameterized class 'A'
        return new A();
    }
}