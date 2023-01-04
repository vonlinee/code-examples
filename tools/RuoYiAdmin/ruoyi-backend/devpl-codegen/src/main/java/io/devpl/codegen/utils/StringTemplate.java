package io.devpl.codegen.utils;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

/**
 * 基于String Template 4
 * 官网：<a href="https://www.stringtemplate.org/">...</a>
 */
public class StringTemplate {

    public static void main(String[] args) {
        test1();
    }

    public static void test1() {
        STGroup group = new STGroupFile("st/test.stg");
        ST st = group.getInstanceOf("decl");
        st.add("type", "int");
        st.add("name", "x");
        st.add("value", 0);
        String result = st.render(); // yields "int x = 0;"
        System.out.println(result);
    }
}
