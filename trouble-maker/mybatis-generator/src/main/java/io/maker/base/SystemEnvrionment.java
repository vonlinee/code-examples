package io.maker.base;

import java.util.Properties;

/**
 * 运行时信息
 */
public class SystemEnvrionment {

    private static final Properties systemEnvs = System.getProperties();
    public static final String OS_NAME = systemEnvs.getProperty("os.name");
    public static final String USER_HOME = systemEnvs.getProperty("user.home");
}
