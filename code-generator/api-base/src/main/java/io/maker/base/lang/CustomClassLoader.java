package io.maker.base.lang;

import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

/**
 * 打破双亲委派机制
 */
public class CustomClassLoader extends URLClassLoader {

    private URL[] urls;

    public CustomClassLoader(URL[] urls) {
        super(urls);
    }
    
    public CustomClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    @Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		return super.findClass(name);
	}

	@Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (urls != null && urls.length > 0) {
            Class<?> target = null;
            try {
                target = findClass(name); //先在自己本身第三方库进行加载
            } catch (ClassNotFoundException exception) {
                target = this.getParent().loadClass(name); //找不到就用父加载器去加载
            }
            return target;
        }
        return super.loadClass(name);
    }
}
