package org.mybatis.generator.internal.util;

import java.io.InputStream;

public class Resources {

    public static InputStream fromClasspath(String pathname) {
        return Resources.class.getResourceAsStream(pathname);
    }
}
