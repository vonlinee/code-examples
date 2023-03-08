package sample.java8.stream;

import lombok.Data;
import sample.java8.io.Printer;
import sample.java8.multithread.question.Print;
import sample.java8.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TestStream {

    public static void main(String[] args) {

        List<Hero> list = getData();

        Map<String, Hero> map = list.stream().collect(Collectors.toMap(Hero::getName, Function.identity()));

        Printer.println(map);
    }

    public static List<Hero> getData() {
        List<Hero> list = new ArrayList<>();
        list.add(new Hero(1, "韩信"));
        list.add(new Hero(2, "曹操"));
        list.add(new Hero(3, "刘备"));
        list.add(new Hero(3, "孙权"));
        return list;
    }
}

@Data
class Hero {
    private int id;
    private String name;

    public Hero(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
