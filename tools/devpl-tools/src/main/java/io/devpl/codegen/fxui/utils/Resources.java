package io.devpl.codegen.fxui.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

/**
 * 资源加载工具类
 */
public final class Resources {

    private static final ClassLoader loader = Resources.class.getClassLoader();

    /**
     * 项目运行时的根路径
     */
    public static final String ROOT_PROJECT_PATH = new File("").getAbsolutePath();

    public static ClassLoader getAppClassLoader() {
        return loader;
    }

    /**
     * 运行时的类路径的根路径
     * 先获得本类的所在位置
     */
    public static final String ROOT_CLASSPATH = Objects.requireNonNull(Resources.class.getResource("/")).getPath();

    public static URL getResource(String pathname) throws FileNotFoundException {
        if (StringUtils.isEmpty(pathname)) {
            throw new NullPointerException("pathname is empty!");
        }
        File file = new File(ROOT_CLASSPATH + resolve(pathname));
        if (!file.exists()) {
            throw new FileNotFoundException(file.getAbsolutePath() + " doesnot exists");
        }
        try {
            return file.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static File getResourcesAsFile(String pathname, boolean reportWhenFileNotExisted) throws FileNotFoundException {
        if (StringUtils.isEmpty(pathname)) {
            throw new NullPointerException("pathname is empty!");
        }
        File file = new File(ROOT_CLASSPATH + resolve(pathname));
        if (!file.exists() && reportWhenFileNotExisted) {
            throw new FileNotFoundException(file.getAbsolutePath() + " doesnot exists");
        }
        return file;
    }

    private static String resolve(String pathname) {
        if (pathname.startsWith("/")) {
            pathname = pathname.substring(1);
        }
        return pathname;
    }

    public static InputStream getResourcesAsStream(String pathname) throws FileNotFoundException {
        if (StringUtils.isEmpty(pathname)) {
            throw new NullPointerException("pathname is empty!");
        }
        if (pathname.startsWith("/")) {
            pathname = pathname.substring(1);
        }
        File file = new File(ROOT_CLASSPATH + pathname);
        if (!file.exists()) {
            throw new FileNotFoundException(file.getAbsolutePath() + " doesnot exists");
        }
        return new BufferedInputStream(new FileInputStream(file));
    }
}
