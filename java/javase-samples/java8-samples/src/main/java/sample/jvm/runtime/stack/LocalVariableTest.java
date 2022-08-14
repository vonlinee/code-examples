package sample.jvm.runtime.stack;

public class LocalVariableTest {

    public static void main(String[] args) {
        LocalVariableTest test = new LocalVariableTest();
        test.method();
    }

    public int method() {
        boolean v1 = true;
        byte v2 = 1;
        char v3 = 2;
        short v4 = 3;
        int v5 = 4;
        float v6 = 5;
        long v7 = 7;
        double v8 = 8;
        Object v9 = new Object();
        return v2 + v4;
    }



    public void test4() {
        int a = 0;
        {
            int b = 0;
            b = a + 1;
        }
        int c = a + 1;
    }


}
