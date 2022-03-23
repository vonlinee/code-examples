package io.maker.base.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 打破双亲委派机制
 * 外部类加载器
 */
public class ExtraClassLoader extends URLClassLoader {

    private static final Logger LOG = LoggerFactory.getLogger(ExtraClassLoader.class);

    private URL[] urls;

    public ExtraClassLoader(URL[] urls) {
        super(urls);
    }

    /**
     * 字节码存放位置
     */
    private final List<String> classLocations = Collections.synchronizedList(new ArrayList<>());

    public void addClassLocation(String name) {
        classLocations.add(name);
    }

    public void removeClassLocation(String name) {
        classLocations.remove(name);
    }

    public ExtraClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
    }

    /**
     * 从本地加载.class文件
     * @param classFile .class文件
     * @return
     */
    public byte[] loadClassFile(File classFile) {
        if (!classFile.exists()) {
            LOG.error(classFile.getAbsolutePath() + "不存在!");
        }
        if (!classFile.getName().endsWith(".class")) {
            LOG.error(classFile.getAbsolutePath() + "不是字节码文件!");
        }
        //检查字节码格式是否正确
        try (FileInputStream fis = new FileInputStream(classFile)) {
            try (ByteArrayOutputStream classBytes = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[2048];
                int len = 0;
                while ((len = fis.read(buffer)) != -1) {
                    classBytes.write(buffer, 0, len);
                }
                return classBytes.toByteArray();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void checkPackageAccess(String packageName) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            int i = packageName.lastIndexOf('.');
            if (i != -1) {
                sm.checkPackageAccess(packageName.substring(0, i));
            }
        }
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
