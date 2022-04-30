package io.maker.base.lang;

import java.io.InputStream;
import java.net.URL;

public class ClassLoaderWrapper {
    ClassLoader defaultClassLoader;
    ClassLoader systemClassLoader;

    public ClassLoaderWrapper() {
        try {
            this.systemClassLoader = ClassLoader.getSystemClassLoader();
        } catch (SecurityException exception) {
            exception.printStackTrace();
        }
    }

    public URL getResourceAsURL(String resource) {
        return this.getResourceAsURL(resource, this.getClassLoaders((ClassLoader) null));
    }

    public URL getResourceAsURL(String resource, ClassLoader classLoader) {
        return this.getResourceAsURL(resource, this.getClassLoaders(classLoader));
    }

    public InputStream getResourceAsStream(String resource) {
        return this.getResourceAsStream(resource, this.getClassLoaders((ClassLoader) null));
    }

    public InputStream getResourceAsStream(String resource, ClassLoader classLoader) {
        return this.getResourceAsStream(resource, this.getClassLoaders(classLoader));
    }

    public Class<?> classForName(String name) throws ClassNotFoundException {
        return this.classForName(name, this.getClassLoaders((ClassLoader) null));
    }

    public Class<?> classForName(String name, ClassLoader classLoader) throws ClassNotFoundException {
        return this.classForName(name, this.getClassLoaders(classLoader));
    }

    InputStream getResourceAsStream(String resource, ClassLoader[] classLoader) {
        ClassLoader[] var3 = classLoader;
        int var4 = classLoader.length;
        for (int var5 = 0; var5 < var4; ++var5) {
            ClassLoader cl = var3[var5];
            if (null != cl) {
                InputStream returnValue = cl.getResourceAsStream(resource);
                if (null == returnValue) {
                    returnValue = cl.getResourceAsStream("/" + resource);
                }

                if (null != returnValue) {
                    return returnValue;
                }
            }
        }
        return null;
    }

    URL getResourceAsURL(String resource, ClassLoader[] classLoader) {
        for (ClassLoader cl : classLoader) {
            if (null != cl) {
                URL url = cl.getResource(resource);
                if (null == url) {
                    url = cl.getResource("/" + resource);
                }
                if (null != url) {
                    return url;
                }
            }
        }
        return null;
    }

    public Class<?> classForName(String name, ClassLoader[] classLoader) throws ClassNotFoundException {
        for (ClassLoader cl : classLoader) {
            if (null != cl) {
                try {
                    return Class.forName(name, true, cl);
                } catch (ClassNotFoundException var8) {
                    var8.printStackTrace();
                }
            }
        }
        throw new ClassNotFoundException("Cannot find class: " + name);
    }

    public ClassLoader[] getClassLoaders(ClassLoader classLoader) {
        return new ClassLoader[]{classLoader, this.defaultClassLoader, Thread.currentThread().getContextClassLoader(), this.getClass().getClassLoader(), this.systemClassLoader};
    }
}