package sample.java8.primary.generic;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 泛型擦除
 */
public class GenericErase<K extends CharSequence, V extends Map<String, V>> {

    public static void main(String[] args) {
        test();
    }

    // 提供约束以保证类型安全
    // 在编译期就能通过类型检查发现问题
    // 尽量使用泛型

    /**
     * 泛型擦除带来的强转问题
     */
    public static void test() {
        Map<Integer, Integer> map1 = new HashMap<>();
        Map<Integer, String> map2 = new HashMap<>();

        Map map3 = new HashMap<Double, Long>();

        Map map4 = map1;
        // 可以放成功
        map4.put("name", "zs");

        Object name = map4.get("name");
        System.out.println(map4.containsKey("name"));
        System.out.println(name);

        System.out.println(map1);

        map1.put(1, 2);

        System.out.println(map1);
    }

    public void test2() {

        Map<Integer, String> map = new LinkedHashMap<>();
        // 可以转换成功
        Class<Map<String, Object>> clazz = (Class<Map<String, Object>>) map.getClass();
        Class<Map> class1 = (Class<Map>) map.getClass();
        Class<?> type = Integer.class;

        System.out.println(clazz);
    }
}
