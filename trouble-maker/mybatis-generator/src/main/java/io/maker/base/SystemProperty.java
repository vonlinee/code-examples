package io.maker.base;

import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * 运行时信息
 */
public class SystemProperty {

    private static final Logger log = Logger.getLogger(SystemProperty.class.getSimpleName());

    private static final Properties systemEnvs = System.getProperties();
    public static final String OS_NAME = systemEnvs.getProperty("os.name");
    public static final String USER_HOME = systemEnvs.getProperty("user.home");
    public static final String USER_DIR = systemEnvs.getProperty("user.dir");
    public static final String OS_VERSION = systemEnvs.getProperty("os.version");

    static {
        log.info("start to load system properties");
    }

    public static void main(String[] args) {

    }
}
