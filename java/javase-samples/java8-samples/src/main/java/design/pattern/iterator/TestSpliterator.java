package design.pattern.iterator;

import java.util.HashMap;
import java.util.Map;
import java.util.Spliterator;

public class TestSpliterator {

    public static void main(String[] args) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("1", "A");
        map.put("2", "B");
        map.put("3", "C");
        map.put("4", "D");

        Spliterator<Map.Entry<String, Object>> spliterator = map.entrySet().spliterator();

        Spliterator<Map.Entry<String, Object>> entrySpliterator = spliterator.trySplit();

        int characteristics = spliterator.characteristics();

        System.out.println(characteristics);


    }


}
