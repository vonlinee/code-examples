package sample.java8.primary.generic;

import java.util.List;
import java.util.Map;

public class GenericMethod {

    public <T> T method() {
        return null;
    }

    public <T> void underLimit(List<T> myList, T limit) {
        for (T e : myList) {
            if (e.hashCode() > limit.hashCode()) System.out.println(e);
        }
    }

    public <T extends Map<String, Object>> T get() {
        return null;
    }

    public static void main(String[] args) {
        GenericMethod method = new GenericMethod();

        Map<?, ?> map = method.get();
    }
}
