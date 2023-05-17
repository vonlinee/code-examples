package generic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Test {

    public void m() {
        List<Foo<?>> list1 = new ArrayList<>();
        List<Foo<String>> list2 = new ArrayList<>();

        list1.add(new Foo<Integer>());
        list1.add(new Foo<String>());

        List<Foo<Object>> list3 = new ArrayList<>();

        List<?> l1 = new ArrayList<>();
        List<Object> l2 = new ArrayList<>();
        List<String> l3 = new ArrayList<>();

        List<?> l11 = l2;
        List<Object> l21 = Collections.singletonList(l3);
    }

    <T> void m1(List<Foo<T>> param) {

    }
}

class Foo<T> {
    private List<?> list = new ArrayList<>();
}
