package utils;

import java.util.Arrays;

public class Logger {

    public static void info(String message) {

    }

    public static void error(String message) {

    }

    public static void println(Object... args) {
        Arrays.stream(args).forEach(System.out::println);
    }
}
