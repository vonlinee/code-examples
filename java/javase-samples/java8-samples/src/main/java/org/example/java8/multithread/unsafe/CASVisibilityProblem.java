package org.example.java8.multithread.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @see AtomicInteger
 */
public class CASVisibilityProblem {
    private int value = 1;
    static Unsafe unsafe;
    static long fieldOffset;

    static {
        try {
            unsafe = getUnsafe();
            fieldOffset = unsafe.objectFieldOffset(CASVisibilityProblem.class.getDeclaredField("value"));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public int getValue() {
        return value;
    }

    public boolean update(int expected, int x) {
        return unsafe.compareAndSwapInt(this, fieldOffset, expected, x);
    }

    public static void main(String[] args) throws InterruptedException {
        CASVisibilityProblem problem = new CASVisibilityProblem();
        new Thread(new Runnable() {
            int i = 0;

            @Override
            public void run() {
                while (problem.getValue() == 1) {
                    i++;
                }
            }
        }).start();

        Thread.sleep(1000);

        if (problem.update(1, 0)) {
            System.out.println(problem.getValue());
        }
    }

    public static Unsafe getUnsafe() throws NoSuchFieldException, IllegalAccessException {
        Unsafe unsafe;
        //通过反射获取unsafe
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        unsafe = (Unsafe) field.get(null);
        return unsafe;
    }
}
