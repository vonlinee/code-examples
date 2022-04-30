package io.maker.base.lang;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.*;

public class Resources {

    private static final ClassLoaderWrapper classLoaderWrapper = new ClassLoaderWrapper();
    private static Charset charset;

    private Resources() {
    }

    public static ClassLoader getDefaultClassLoader() {
        return classLoaderWrapper.defaultClassLoader;
    }

    public static void setDefaultClassLoader(ClassLoader defaultClassLoader) {
        classLoaderWrapper.defaultClassLoader = defaultClassLoader;
    }

    public static URL getResourceURL(String resource) throws IOException {
        return getResourceURL((ClassLoader) null, resource);
    }

    public static URL getResourceURL(ClassLoader loader, String resource) throws IOException {
        URL url = classLoaderWrapper.getResourceAsURL(resource, loader);
        if (url == null) {
            throw new IOException("Could not find resource " + resource);
        } else {
            return url;
        }
    }

    public static InputStream getResourceAsStream(String resource) throws IOException {
        return getResourceAsStream((ClassLoader) null, resource);
    }

    public static InputStream getResourceAsStream(ClassLoader loader, String resource) throws IOException {
        InputStream in = classLoaderWrapper.getResourceAsStream(resource, loader);
        if (in == null) {
            throw new IOException("Could not find resource " + resource);
        } else {
            return in;
        }
    }

    public static Properties getResourceAsProperties(String resource) throws IOException {
        Properties props = new Properties();
        InputStream in = getResourceAsStream(resource);
        Throwable var3 = null;

        try {
            props.load(in);
        } catch (Throwable var12) {
            var3 = var12;
            throw var12;
        } finally {
            if (in != null) {
                if (var3 != null) {
                    try {
                        in.close();
                    } catch (Throwable var11) {
                        var3.addSuppressed(var11);
                    }
                } else {
                    in.close();
                }
            }

        }

        return props;
    }

    public static Properties getResourceAsProperties(ClassLoader loader, String resource) throws IOException {
        Properties props = new Properties();
        InputStream in = getResourceAsStream(loader, resource);
        Throwable var4 = null;

        try {
            props.load(in);
        } catch (Throwable var13) {
            var4 = var13;
            throw var13;
        } finally {
            if (in != null) {
                if (var4 != null) {
                    try {
                        in.close();
                    } catch (Throwable var12) {
                        var4.addSuppressed(var12);
                    }
                } else {
                    in.close();
                }
            }

        }

        return props;
    }

    public static Reader getResourceAsReader(String resource) throws IOException {
        InputStreamReader reader;
        if (charset == null) {
            reader = new InputStreamReader(getResourceAsStream(resource));
        } else {
            reader = new InputStreamReader(getResourceAsStream(resource), charset);
        }

        return reader;
    }

    public static Reader getResourceAsReader(ClassLoader loader, String resource) throws IOException {
        InputStreamReader reader;
        if (charset == null) {
            reader = new InputStreamReader(getResourceAsStream(loader, resource));
        } else {
            reader = new InputStreamReader(getResourceAsStream(loader, resource), charset);
        }

        return reader;
    }

    public static File getResourceAsFile(String resource) throws IOException {
        return new File(getResourceURL(resource).getFile());
    }

    public static File getResourceAsFile(ClassLoader loader, String resource) throws IOException {
        return new File(getResourceURL(loader, resource).getFile());
    }

    public static InputStream getUrlAsStream(String urlString) throws IOException {
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        return conn.getInputStream();
    }

    public static Reader getUrlAsReader(String urlString) throws IOException {
        InputStreamReader reader;
        if (charset == null) {
            reader = new InputStreamReader(getUrlAsStream(urlString));
        } else {
            reader = new InputStreamReader(getUrlAsStream(urlString), charset);
        }

        return reader;
    }

    public static Properties getUrlAsProperties(String urlString) throws IOException {
        Properties props = new Properties();
        InputStream in = getUrlAsStream(urlString);
        Throwable var3 = null;

        try {
            props.load(in);
        } catch (Throwable var12) {
            var3 = var12;
            throw var12;
        } finally {
            if (in != null) {
                if (var3 != null) {
                    try {
                        in.close();
                    } catch (Throwable var11) {
                        var3.addSuppressed(var11);
                    }
                } else {
                    in.close();
                }
            }

        }

        return props;
    }

    public static Class<?> classForName(String className) throws ClassNotFoundException {
        return classLoaderWrapper.classForName(className);
    }

    public static Charset getCharset() {
        return charset;
    }

    public static void setCharset(Charset charset) {
        Resources.charset = charset;
    }

    public static final String PROJECT_ROOT_PATH = new File("").getAbsolutePath();
    public static final String USER_DIR = System.getProperty("user.dir");

    /**
     * 获取所有的类路径 包括jar包的路径
     * @return Array
     */
    public static String[] getClassPaths() {
        return System.getProperty("java.class.path").split(";");
    }

    /**
     * 获取类路径，以file://开头
     * @return file://c:/xxx/xxx
     */
    public static String getFileSystemClassPath() {
        return Objects.requireNonNull(Resources.class.getClassLoader().getResource("")).toString();
    }

    /**
     * 获取类路径，普通文件路径
     * @return classpath
     */
    public static String getClassPath() {
        URL resource = Resources.class.getResource("/");
        String classpath = "";
        if (resource != null && (classpath = resource.getPath()).length() != 0) {
            classpath = classpath.substring(1);
        }
        return classpath;
    }

    /**
     * @param name 资源位置
     * @return Properties
     */
    public static Properties loadProperties(String name) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(resolveName(name)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * 根据输入解析路径
     * @param name 路径名
     * @return
     */
    private static String resolveName(String name) {
        String classPath = getClassPath();
        if (name.startsWith("..")) {
            // TODO 解析..的个数，判断相对目录的层级，然后向前缩进几个目录
            name = name + name;
        }
        if (name.startsWith(".")) {
            return classPath + File.separator + name;
        }
        if (!name.startsWith("/")) {
            name = classPath + File.separator + name;
        } else {
            name = classPath + File.separator + name.substring(1);
        }
        return name;
    }

    public static URL getResourceAsURL(String name) {
        return Resources.class.getResource(resolveName(name));
    }

    public static void m1() throws IOException {
        InputStream inputStream = ClassLoader.getSystemResourceAsStream("config/application.properties");
        ResourceBundle resourceBundle = new PropertyResourceBundle(inputStream);
        Enumeration<String> keys = resourceBundle.getKeys();
        while (keys.hasMoreElements()) {
            String s = keys.nextElement();
            System.out.println(s + " = " + resourceBundle.getString(s));
        }
    }

    /**
     * 8. 方式八
     * ResourceBundle.getBundle的路径访问和 Class.getClassLoader.getResourceAsStream类似，默认从根目录下读取，也可以读取resources目录下的文件
     * ResourceBundle rb = ResourceBundle.getBundle("b") //不需要指定文件名的后缀,只需要写文件名前缀即可
     */
    public void test8() {
        //ResourceBundle rb = ResourceBundle.getBundle("jdbc"); //读取resources目录下的jdbc.properties
        ResourceBundle rb2 = ResourceBundle.getBundle("config/application");//读取resources/config目录下的application.properties
        for (String key : rb2.keySet()) {
            String value = rb2.getString(key);
            System.out.println(key + ":" + value);
        }
    }
}
