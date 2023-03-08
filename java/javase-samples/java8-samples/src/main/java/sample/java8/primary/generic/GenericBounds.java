package sample.java8.primary.generic;

import org.junit.jupiter.api.Test;
import sample.java8.primary.generic.bean.Apple;
import sample.java8.primary.generic.bean.Fruit;
import sample.java8.primary.generic.bean.Orange;

import java.util.ArrayList;
import java.util.List;

public class GenericBounds {

    @Test
    public void test1() {
        List<Fruit> fruits = new ArrayList<>();
        fruits.add(new Fruit());
        processList(fruits);

        fruits.add(new Orange());

        System.out.println("====================");
        for (Fruit fruit : fruits) {
            System.out.println(fruit);
        }
    }

    public <T> void processList1(List<? super Orange> list) {
        // Object obj = list.get(0); // 类型丢失
        list.add(new Orange());
        // list.add(new Fruit()); // capture of ? super Apple
    }

    public <T> void processList(List<? super Apple> list) {
        // Object obj = list.get(0); // 类型丢失
        list.add(new Apple());
        // list.add(new Fruit()); // capture of ? super Apple
    }
}
