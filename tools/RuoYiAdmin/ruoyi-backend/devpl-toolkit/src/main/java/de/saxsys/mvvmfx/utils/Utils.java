package de.saxsys.mvvmfx.utils;

public class Utils {

    public static void printlnObj(Object obj) {
        if (obj == null) {
            return;
        }
        System.out.println(obj.getClass().getName() + "@" + Integer.toHexString(obj.hashCode()));
    }
}
