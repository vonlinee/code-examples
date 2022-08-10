package sample.jvm.classloader;

import sun.misc.Launcher;
import sun.misc.URLClassPath;
import sun.net.spi.nameservice.dns.DNSNameService;

import java.net.URL;

/**
 * @author vonline
 * @since 2022-07-24 0:39
 */
public class TestClassLoader {

    public static void main(String[] args) {
        //test4();
        test6();
    }

    public static void test1() {
        // DNSNameService类位于 JAVA_HOME/jre/lib/ext/dnsns.jar
        ClassLoader classLoader = DNSNameService.class.getClassLoader();
        System.out.println(classLoader); //sun.misc.Launcher$ExtClassLoader@70dea4e
        String extDirs = System.getProperty("java.ext.dirs");
        for (String path : extDirs.split(";")) {
            System.out.println(path);
        }
        //D:\Develop\Environment\JDK\jdk8u301\jre\lib\ext
        //C:\WINDOWS\Sun\Java\lib\ext
    }

    public static void test2() {
        ClassLoader classLoader = DNSNameService.class.getClassLoader();
        ClassLoader parent = classLoader.getParent();
        System.out.println(parent); // null
    }

    public static void test6() {
        Launcher launcher = Launcher.getLauncher();
        ClassLoader classLoader = launcher.getClassLoader();
        System.out.println(classLoader); // sun.misc.Launcher$AppClassLoader@18b4aac2
        // 获取BootstrapClassLoader能够加载的URL路径
        URLClassPath urlClassPath = Launcher.getBootstrapClassPath();
        for (URL url : urlClassPath.getURLs()) {
            System.out.println(url);
        }
    }

    public static void test3() {
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        ClassLoader appClassLoader = TestClassLoader.class.getClassLoader();
        // true => 我们自己写的的Java应用的类都是由它来完成加载
        System.out.println(systemClassLoader == appClassLoader);
        ClassLoader extClassLoader = DNSNameService.class.getClassLoader();
        ClassLoader appParent = appClassLoader.getParent();
        // true => 父类加载器为扩展类加载器
        System.out.println(appParent == extClassLoader);
        // sun.misc.Launcher$AppClassLoader@73d16e93
        System.out.println(appClassLoader);
// Eclipse中是项目根路径下的目录：..\jvm-sample\bin
// Idea中除了程序的路径，输出还包括其他加载路径
        String extDirs = System.getProperty("java.class.path");
        for (String path : extDirs.split(";")) {
            System.out.println(path);
        }
    }

    public static void test4() {
        System.out.println();
    }
}
