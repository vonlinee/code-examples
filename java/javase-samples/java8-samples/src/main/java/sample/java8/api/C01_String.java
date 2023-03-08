package sample.java8.api;

public class C01_String {

    public static void main(String[] args) {
        String s1 = "a" + "b" + "c";

        for (int i = 0; i < 100; i++) {
            s1 = s1 + "" + i;
        }
        System.out.println();
    }

}
