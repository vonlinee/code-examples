package sample;

import org.junit.Test;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

/**
 * https://blog.csdn.net/it_freshman/article/details/122717533
 */
public class Test1 {

    @Test
    public void test1() {
        ST hello = new ST("Hello <name>");
        hello.add("name", "World");
        System.out.println(hello.render());
    }

    @Test
    public void test2() {
        STGroup stGroup = new STGroup();
        // 模板名称，模板参数，模板内容
        stGroup.defineTemplate("thing", "name", "<name>");
        stGroup.defineTemplate("say", "name", "Hello <thing(name)>");
        ST st = stGroup.getInstanceOf("say");
        st.add("name", "World");
        System.out.println(st.render());
    }

    // .st  单个模板文件
    // .stg 模板组文件
    @Test
    public void test3() {
        STGroup group = new STGroupFile(FILE);
        ST st = group.getInstanceOf("decl");
        st.add("type", "int");
        st.add("name", "x");
        // st.add("value", 1);
        String result = st.render(); // yields "int x = 0;"

        System.out.println(st.render());
    }

    @Test
    public void test4() {
        ST st = new ST("<b>$u.id$</b>: $u.name$", '$', '$');
        st.add("u", new User(999, "parrt"));
        String result = st.render(); // "<b>999</b>: parrt"
        System.out.println(result);
    }

    @Test
    public void test5() throws InterruptedException {
        ST st = new ST("<items:{it|<it.id>: <it.lastName>, <it.firstName>\n}>");
        st.addAggr("item.{ firstName ,lastName, id }", "Ter", "Parr", 99); // add() uses varargs
        st.addAggr("items.{firstName, lastName ,id}", "Tom", "Burns", 34);
        String expecting = "99: Parr, Ter\n" + "34: Burns, Tom\n";
        System.out.println(st.render());
    }

    @Test
    public void test6() {
        int[] num =
                new int[]{3, 9, 20, 2, 1, 4, 6, 32, 5, 6, 77, 888, 2, 1, 6, 32, 5, 6, 77,
                        4, 9, 20, 2, 1, 4, 63, 9, 20, 2, 1, 4, 6, 32, 5, 6, 77, 6, 32, 5, 6, 77,
                        3, 9, 20, 2, 1, 4, 6, 32, 5, 6, 77, 888, 1, 6, 32, 5};
        String t = ST.format(30, "int <%1>[] = { <%2; wrap, anchor, separator=\", \"> };", "a", num);
    }

    private final String FILE = "D:\\Code\\code-samples\\tools\\string-template-learn\\src\\main\\java\\sample\\test.stg";

    @Test
    public void test7() {
        // STGroup.verbose = true;
        STGroup group = new STGroupFile(FILE);
        ST bracket = group.getInstanceOf("bracket");

        bracket.add("x", 12);

        ST test = group.getInstanceOf("test2");

        test.add("name", "zs");

        System.out.println(test.render());
    }
}
