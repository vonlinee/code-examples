package org.example;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.reflect.Method;

public class CreateClassExample {
  public static void main(String[] args) throws Exception {
    ClassPool pool = ClassPool.getDefault();
    // 创建一个新的类
    CtClass newClass = pool.makeClass("com.example.MyClass");
    // 添加一个方法
    CtMethod method = CtMethod.make("public void sayHello() { System.out.println(\"Hello, World!\"); }", newClass);
    newClass.addMethod(method);
    // 将类加载到 JVM 中
    Class<?> clazz = newClass.toClass();
    // 实例化并调用方法
    Object obj = clazz.getConstructor().newInstance();
    Method sayHello = obj.getClass().getDeclaredMethod("sayHello");
    sayHello.invoke(obj);
  }
}