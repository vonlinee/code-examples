package sample.java8.collections.list;

public class TestArrayCopy {

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 5, 6};
        int[] b = {10, 20, 30, 40, 50, 60};
        System.arraycopy(a, 1, b, 2, 3);
        //10 20 2 3 4 60
    }
}
