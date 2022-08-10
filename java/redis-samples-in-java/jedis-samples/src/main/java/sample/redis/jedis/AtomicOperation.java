package sample.redis.jedis;

/**
 * @author vonline
 * @since 2022-07-29 21:27
 */
public class AtomicOperation {

    public static void main(String[] args) {
        int i = 1;
        i++;
    }

    public synchronized int method() {
        int i = 1;
        i++;
        int j = i--;
        return j;
    }
}
