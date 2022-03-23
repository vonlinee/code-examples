package code.sample.jvm;

public class JavapTest {

    private int num;
    boolean flag;
    protected char gender;
    public String info;

    public static final int COUNTS = 1;

    static {
        System.out.println("Hello World");
    }

    {
        flag = false;
    }

    public void method1() {
    }

    void method2() {
        this.info = super.getClass().getName();
    }

    protected int method3(String name, int age) {
        return age + name.length();
    }
}
