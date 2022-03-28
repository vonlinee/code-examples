package io.pocket.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * 用于测试加载工程内部的文件
 */
public final class ResourceLoader {

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
        return Objects.requireNonNull(ResourceLoader.class.getClassLoader().getResource("")).toString();
    }

    /**
     * 获取类路径，普通文件路径
     * @return classpath
     */
    public static String getClassPath() {
        URL resource = ResourceLoader.class.getResource("/");
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

    public static InputStream getResourceAsStream(String name) {
        return ResourceLoader.class.getResourceAsStream(resolveName(name));
    }

    public static URL getResourceAsURL(String name) {
        return ResourceLoader.class.getResource(resolveName(name));
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
