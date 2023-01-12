package use;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DocUtils {

    public static <V> List<String> toDocText(Map<String, V> map) {
        final LinkedList<String> list = new LinkedList<>();
        int i = 0;
        for (Map.Entry<String, V> entry : map.entrySet()) {
            i++;
            list.add(i + ": " + entry.getKey() + " => " + entry.getValue().getClass().getSimpleName());
        }
        return list;
    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);

        toDocText(map).forEach(System.out::println);

    }
}
