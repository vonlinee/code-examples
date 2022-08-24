package sample.java.primary.generic;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import sample.java.primary.generic.bean.Apple;
import sample.java.primary.generic.bean.Fruit;
import thirdlib.xml.Person;

public class GenericWildcard {

    @Test
    public void test1() {

        List<Apple> apples = new ArrayList<>();
        // The method printList(List<Fruit>) in the type GenericWildcard
        // is not applicable for the arguments (List<Apple>)
        // printList(apples);

        List<Fruit> fruits = new ArrayList<>();
        // printList2(fruits);

    }

    private void printList(List<Fruit> list) {
        list.add(new Apple());
    }

    private void printList1(List<?> list) {
        Object o = list.get(0);
    }

    private void printList2(List<Object> list) {
        Object o = list.get(0);
    }

    private void printList3(List<? extends Fruit> list) {
        Fruit fruit = list.get(0);

    }
}
