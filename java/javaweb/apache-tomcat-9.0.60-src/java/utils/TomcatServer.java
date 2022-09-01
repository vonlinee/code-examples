package utils;

import org.apache.catalina.startup.Bootstrap;

import java.io.File;

/**
 * -Dcatalina.home=
 * -Dcatalina.base=D:/Projects/apache-tomcat-9.0.60-src/home
 * -Djava.endorsed.dirs=catalina-home/endorsed
 * -Djava.io.tmpdir=catalina-home/temp
 * -Djava.util.logging.manager=org.apache.juli.ClassLoaderLogManager
 * -Djava.util.logging.config.file=D:/Projects/apache-tomcat-9.0.60-src/home/conf/logging.properties
 */
public class TomcatServer {

    private static final String PROJECT_ROOT_PATH = new File("").getAbsolutePath();

    static {
        System.setProperty("catalina.home", PROJECT_ROOT_PATH + "/home");
        System.setProperty("catalina.base", PROJECT_ROOT_PATH + "/home");
        System.setProperty("java.endorsed.dirs", PROJECT_ROOT_PATH + "/home/endorsed");
        System.setProperty("java.io.tmpdir", PROJECT_ROOT_PATH + "/home/temp");
        System.setProperty("java.util.logging.manager", "org.apache.juli.ClassLoaderLogManager");
        System.setProperty("java.util.logging.config.file", PROJECT_ROOT_PATH + "/home/conf/logging.properties");
    }

    public static void main(String[] args) {
        Bootstrap.main(args);
    }
}
