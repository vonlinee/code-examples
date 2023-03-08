package sample.java8.multithread.unsafe;

import org.junit.jupiter.api.Test;

/**
 * https://blog.csdn.net/u014783007/article/details/120533732
 */
public class TestUnsafe {


    @Test
    public void testVm() {
        Model model = new Model();
        model.setId(200);
        model.setName("zs");

        Model newModel = new Model(2, "ls", null);
        System.out.println(model);
        System.out.println(newModel);
        long offset = UnsafeOps.objectFieldOffset(Model.class, "model");

        boolean b = UnsafeOps.compareAndSwapObject(model, offset, null, newModel);

        if (b) {
            System.out.println("交换成功");
            System.out.println(model);
            System.out.println(newModel);
        }
    }
}
