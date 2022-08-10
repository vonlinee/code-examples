package sample.java.collections.map;

import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.Properties;

public class TestHashTable {

    public static void main(String[] args) throws Exception {
        // test2();
        test1();
    }

    public static void test1() throws NoSuchFieldException, IllegalAccessException {
        Hashtable<String, String> table = new Hashtable<>();
        Field tableField = table.getClass().getDeclaredField("table");
        Field thresholdField = table.getClass().getDeclaredField("threshold");
        tableField.setAccessible(true);
        thresholdField.setAccessible(true);
        int oldLen = 0;
        for (int i = 0; i < 100; i++) {
            table.put("key-" + i, "value-" + i);
            Object[] entries = (Object[]) tableField.get(table);
            if (oldLen != entries.length) {
                oldLen = entries.length;
                System.out.println(entries.length + "\t" + thresholdField.get(table));
            }
        }
    }

    public static void test2() {
        for (int i = 0; i < 100; i++) {
            String key = "key-" + i;
            System.out.println(key.hashCode() & 0x7FFFFFFF % 11);
        }
    }

    public static void test3() {
        for (int i = 0; i < 100; i++) {
            String key = "key-" + i;
            int hash1 = key.hashCode();
            // 第一次
            int index = key.hashCode() & 0x7FFFFFFF % 11;
        }
    }
}
