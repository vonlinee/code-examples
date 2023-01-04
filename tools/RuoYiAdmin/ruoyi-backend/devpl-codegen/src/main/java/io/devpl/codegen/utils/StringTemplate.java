package io.devpl.codegen.utils;

import org.junit.Test;
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
        st.add("value", 10);
        String result = st.render(); // yields "int x = 0;"
        System.out.println(result);
    }

    @Test
    public void test2() {
        ST st = new ST("<name; separator=\", \">");
        st.add("name", "zs");
        st.add("name", "ls");
        System.out.println(st.render());
    }

    @Test
    public void test3() {
        STGroup group = new STGroupFile("st/test.stg");
        ST st = group.getInstanceOf("test");
        st.add("name", "zs");
        st.add("name", "ls");
        st.add("name", "ww");
        String result = st.render(); // yields "int x = 0;"
        System.out.println(result);
    }

    @Test
    public void testAggrete() {
        ST st = new ST("<items:{it|<it.id>: <it.lastName>, <it.firstName>\n}>");
        st.addAggr("items.{ firstName ,lastName, id }", "Ter", "Parr", 99); // add() uses varargs
        st.addAggr("items.{firstName, lastName ,id}", "Tom", "Burns", 34);
        String expecting = "99: Parr, Ter\n" + "34: Burns, Tom\n";
    }
}
