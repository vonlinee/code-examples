package org.example.jvm.loadclass;

import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

/**
 * 自定义类加载器，打破双亲委派模式
 */
public class CustomClassLoader extends URLClassLoader {

    URL[] urls;

    public CustomClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public CustomClassLoader(URL[] urls) {
        super(urls);
    }

    public CustomClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(urls, parent, factory);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (urls != null && urls.length > 0) {
            Class<?> target = null;
            try {
                //先在自己本身第三方库进行加载
                target = findClass(name);
            } catch (ClassNotFoundException exception) {
                //找不到就用父加载器去加载
                target = super.loadClass(name); //这句其实有问题，应该是下面这句
                target = this.getParent().loadClass(name);
            }
            return target;
        }
        return super.loadClass(name);
    }
}
