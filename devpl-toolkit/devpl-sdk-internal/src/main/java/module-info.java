module devpl.sdk.internal {
    requires com.google.common;
    requires java.sql;
    requires lombok;
    requires spring.core;
    requires jsr305;
    requires java.net.http;
    requires java.datatransfer;
    requires java.desktop;

    exports io.devpl.sdk;
    exports io.devpl.sdk.util;
    exports io.devpl.sdk.lang;
}