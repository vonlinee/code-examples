package org.example.jvm.gc.algorithm;

import org.openjdk.jol.vm.VM;

import java.util.concurrent.TimeUnit;

/**
 * @author vonline
 * @since 2022-07-24 20:34
 */
public class ReliveObject {

    static ReliveObject obj;

    public static void main(String[] args) {

        System.out.println(VM.current().details());
        try {
            obj = new ReliveObject();
            // 对象第一次成功拯救自己
            obj = null;
            System.gc(); // 调用GC
            System.out.println("第1次GC");
            TimeUnit.SECONDS.sleep(2);
            if (obj == null) {
                System.out.println("obj is dead");
            } else {
                System.out.println("obj is still alive");
            }
            obj = null;
            System.out.println("第2次GC");
            System.gc(); // 调用GC
            if (obj == null) {
                System.out.println("obj is dead");
            } else {
                System.out.println("obj is still alive");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize");
        obj = this;
    }
}
