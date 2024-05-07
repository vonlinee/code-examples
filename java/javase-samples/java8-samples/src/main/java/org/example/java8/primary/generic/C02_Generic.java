package org.example.java8.primary.generic;

import org.example.java8.primary.generic.bean.Apple;
import org.example.java8.primary.generic.bean.Fruit;

import java.util.ArrayList;
import java.util.List;

public class C02_Generic {

    public static void main(String[] args) {

    }

    public static <E> List<? super E> m(List<? super E> list) {
        return list;
    }

    public static void test2() {
        List<? extends Fruit> fruits;
        // 右边的泛型Fruit可以省略，Explicit type argument Fruit can be replaced with <>
        fruits = new ArrayList<Fruit>();
        // 这里右边的泛型Apple就不可以省略
        fruits = new ArrayList<Apple>();

//        fruits = new ArrayList<Object>();//编译不通过

//        fruits.add(new Fruit());//编译不通过
//        fruits.add(new Apple());//编译不通过
    }

    public static void test3() {
        List<? super Apple> apples;
        apples = new ArrayList<Apple>();
        apples = new ArrayList<Fruit>();
        apples = new ArrayList<Object>();

        apples.add(new Apple());
//        apples.add(new RedApple());
    }



}
