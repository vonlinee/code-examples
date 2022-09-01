package sample.spring.boot.ext;

public class Utils {

    public static String toString(Object obj) {
        if (obj == null) return "null";
        return obj.getClass().getSimpleName() + "@" + Integer.toHexString(obj.hashCode());
    }

    public static void printObject(Object obj) {
        System.out.println(" => " + toString(obj));
    }
}
