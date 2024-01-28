package sample;

import org.junit.Test;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.io.File;

public class TestFunction {

    @Test
    public void test1() {
        File file = Utils.searchStgFile("function");

        STGroup group = new STGroupFile(file.getAbsolutePath());
        ST st = group.getInstanceOf("sql");

        System.out.println(st.render());
    }
}
