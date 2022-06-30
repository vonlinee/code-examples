package sample.java.primary;

public class C01_ValuePass {

    public static void main(String[] args) {
        String s1 = "A";
        String s2 = "B";
        System.out.printf("%s, %s\n", s1, s2);
        swap(s1, s2);
        System.out.printf("%s, %s\n", s1, s2);
    }

    public static void swap(String s1, String s2) {
        String tmp = s1;
        s1 = s2;
        s2 = tmp;
    }
}
