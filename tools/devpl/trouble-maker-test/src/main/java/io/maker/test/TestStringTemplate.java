package io.maker.test;

import org.stringtemplate.v4.ST;

public class TestStringTemplate {

    public static void main(String[] args) {
        ST hello = new ST("Hello, <name>");
        hello.add("name", "World");
        System.out.println(hello.render());

    }
}
