package io.maker.base;

import io.maker.base.lang.NamedValue;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        //String i = ObjectUtils.safeCast(new Object(), String.class);
        //System.out.println(i);
        //String sss = FileUtils.readFileToString(new File("1.txt"));
        //FileUtils.writeString(new File("1.txt"), sss);

        NamedValue namedValue = new NamedValue(null, "A");

        System.out.println(namedValue);

        System.out.println(namedValue.type());
    }
}
