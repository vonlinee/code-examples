package org.sqltemplate;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.gui.STViz;

import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        STGroupFile stf = new STGroupFile("D:\\Develop\\Code\\code-samples\\java\\jdbc-sql-template\\src\\test\\java\\test.stg");

        ST st = stf.getInstanceOf("selectList");
        st.add("name", "zs");
        st.add("code", "sd sd");
        st.add("list", Arrays.asList(1, 3, 4, 5));
        String result = st.render();

        STViz.test1();
        System.out.println(result);
    }
}