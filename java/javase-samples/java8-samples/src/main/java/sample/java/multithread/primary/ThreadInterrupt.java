package sample.java.multithread.primary;

<<<<<<< HEAD
/**
 * @author vonline
 * @since 2022-08-25 23:30
 */
public class ThreadInterrupt {

    public static void main(String[] args) throws InterruptedException {
        test1();
    }

    private static void test1() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage()); // sleep interrupted
            }
        }, "t1");
        t1.start();
        Thread.sleep(500);
        t1.interrupt();
        Thread.sleep(100);
        System.out.printf(" interrupt status : %s\n", t1.isInterrupted());
    }

    public static void test2() throws InterruptedException {
        Thread t3 = new Thread(() -> {
            log.debug("t3 park.....");
            LockSupport.park();
            log.debug("t3 unpark.....");
            log.debug("interrupt status: [{}]", Thread.currentThread().isInterrupted());

            log.debug("t3 第二次 park.....");
            LockSupport.park();
            log.debug("t3 中断位为true, park失效.....");
        }, "t3");
        t3.start();
        Thread.sleep(1000);
        t3.interrupt();
    }

=======
public class ThreadInterrupt {


    public static void main(String[] args) {
        if (Thread.interrupted())  {
            try {
                throw new InterruptedException();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
>>>>>>> a0daecb8e92f85e360640922a54e05ddb9c36867
}
