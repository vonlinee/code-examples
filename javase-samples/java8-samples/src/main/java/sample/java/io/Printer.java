package sample.java.io;

import java.util.List;
import java.util.Map;

public class Printer {

    public static void println(Map<?, ?> map) {
        for (Object key : map.keySet()) {
            System.out.println(key + " - " + map.get(key));
        }
    }

    public static void println(List<?> list) {
        for (Object obj : list) {
            System.out.println(obj);
        }
    }

    public static <T> void println(T[] array) {
        for (T t : array) {
            System.out.println(t);
        }
    }

    public static void println(String[] strings) {
        for (String s : strings) {
            System.out.println(s);
        }
    }
}
