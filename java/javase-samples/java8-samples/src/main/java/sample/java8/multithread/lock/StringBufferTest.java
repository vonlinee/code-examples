package sample.java8.multithread.lock;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.StringJoiner;

public class StringBufferTest {

    public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException {
        ArrayList<Integer> list = new ArrayList<>(2);
        Field field = ArrayList.class.getDeclaredField("elementData");
        field.setAccessible(true);
        Object[] elementData;
        int oldSize = 0;
        StringJoiner res = new StringJoiner(" -> ");
        for (int i = 0; i < 100; i++) {
            list.add(i);
            elementData = (Object[]) field.get(list);
            if (elementData.length != oldSize) {
                res.add((String.valueOf(elementData.length)));
                oldSize = elementData.length;
            }
        }
        System.out.println(res);
    }


}