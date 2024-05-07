package org.example.java8.primary.generic;

import java.util.ArrayList;
import java.util.List;

import org.example.java8.primary.generic.bean.Apple;
import org.example.java8.primary.generic.bean.WaterMelon;

public class C01_GenericPrimary {

    public static void main(String[] args) {


//        List<Object> list1 = null;
//
//        ArrayList<Object> list2 = new ArrayList<>();
//
//        list1 = list2;
//
//        ArrayList<Fruit> list3 = new ArrayList<>();
//
//        // list1 = list3;

        // m1("AAA");

        test1();
    }

    public static void test1() {
        List apples = new ArrayList();
        apples.add(new Apple());
        apples.add(new WaterMelon());

        // Object o = apples.get(0);
        Apple apple1 = (Apple) apples.get(0);
        Apple apple2 = (Apple) apples.get(1);
    }


    public static <T> void m1(T p) {
        System.out.println(p instanceof Object);
    }

    public static <T> void m2(T p) {

    }
}
