package io.devpl.codegen.mbg.utils;

import java.net.URL;

public class Resources {

    public static URL fromClasspath(String pathname) {
        if (pathname.startsWith("/")) {
            pathname = pathname.substring(1);
        }
        return Thread.currentThread().getContextClassLoader().getResource(pathname);
    }
}
