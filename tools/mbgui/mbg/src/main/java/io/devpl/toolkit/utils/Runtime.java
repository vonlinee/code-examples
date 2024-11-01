package io.devpl.toolkit.utils;

public final class Runtime {

    private static final String OS = System.getProperty("os.name").toLowerCase();

    public static boolean isLinux() {
        return OS.contains("linux");
    }

    public static boolean isMacOS() {
        return OS.contains("mac") && OS.contains("os") && OS.contains("x");
    }

    public static boolean isMacOSX() {
        return OS.contains("mac") && OS.contains("os") && OS.contains("x");
    }

    public static boolean isWindows() {
        return OS.contains("windows");
    }


}