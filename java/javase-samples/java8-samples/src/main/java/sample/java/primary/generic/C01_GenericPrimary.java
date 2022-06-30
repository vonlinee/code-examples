package sample.java.primary.generic;

public class C01_GenericPrimary {

    public static void main(String[] args) {

//        List<Object> list1 = null;
//
//        ArrayList<Object> list2 = new ArrayList<>();
//
//        list1 = list2;
//
//        ArrayList<Fruit> list3 = new ArrayList<>();
//
//        // list1 = list3;

        m1("AAA");
    }

    public static <T> void m1(T p) {
        System.out.println(p instanceof Object);
    }

    public static <T> void m2(T p) {

    }
}
