package org.example.jvm.loadclass;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class TestUrlClassLoader {
    public static void main(String[] args) throws Exception {
        File file = new File("");
        URL[] urls = new URL[]{file.toURI().toURL()};
        URLClassLoader classLoader = new URLClassLoader(urls);
        Class<?> clazz = classLoader.loadClass("com.test.A");
        Object obj = clazz.newInstance();
        Method method = clazz.getMethod("methodName");
        Object returnValue = method.invoke(obj);
    }
}
