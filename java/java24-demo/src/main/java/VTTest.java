import java.util.concurrent.TimeUnit;

public class VTTest {

    public static void main(String[] args) throws InterruptedException {

        Thread thread = Thread.ofVirtual().start(new Runnable() {
            @Override
            public void run() {

            }
        });

        TimeUnit.MINUTES.sleep(3);
    }
}
