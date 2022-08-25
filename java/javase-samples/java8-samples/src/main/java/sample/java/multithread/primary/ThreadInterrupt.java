package sample.java.multithread.primary;

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
}
