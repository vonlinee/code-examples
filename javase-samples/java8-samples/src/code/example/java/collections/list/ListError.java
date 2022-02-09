package code.example.java.collections.list;

import java.util.ArrayList;

public class ListError {
    public static void main(String[] args) {
        ArrayList<Integer> arrayLists = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            arrayLists.add(i);
        }
        for (Integer num : arrayLists) {
            if (num.equals(3)) {
                arrayLists.remove(new Integer(3));
            }
        }


    }
}