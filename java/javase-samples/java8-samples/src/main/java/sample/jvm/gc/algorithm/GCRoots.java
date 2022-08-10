package sample.jvm.gc.algorithm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * @author vonline
 * @since 2022-07-24 20:46
 */
public class GCRoots {

    public static void main(String[] args) {
        List<Object> numlist = new ArrayList<>();
        Date birth = new Date();
        for (int i = 0; i < 100; i++) {
            numlist.add(String.valueOf(i));
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("数据添加完毕");
        new Scanner(System.in).next();

        numlist = null;
        birth = null;

        System.out.println("数据置空完毕");
        new Scanner(System.in).next();
    }
}
