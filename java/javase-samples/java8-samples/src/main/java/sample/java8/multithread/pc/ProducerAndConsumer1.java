package sample.java8.multithread.pc;

import java.util.LinkedList;

/**
 * https://blog.csdn.net/liulizhi1996/article/details/119082097
 * https://www.cnblogs.com/Ming8006/p/7243858.html
 */
public class ProducerAndConsumer1 {

    public static void main(String[] args) {
        Storage storage = new Storage();
        for (int i = 1; i < 6; i++) {
            int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    storage.produce(String.format("生产者%d:", finalI));
                }
            }).start();
        }

        for (int i = 1; i < 4; i++) {
            int finalI = i;
            new Thread(() -> storage.consume(String.format("消费者%d:", finalI))).start();
        }
    }
}


class Storage {
    // 仓库最大存储量
    private final int MAX_SIZE = 100;
    // 仓库存储的载体
    private final LinkedList<Object> list = new LinkedList<>();

    // 生产产品
    public void produce(String producer) {
        synchronized (list) {
            // 如果仓库已满
            while (list.size() == MAX_SIZE) {
                System.out.println("仓库已满，【" + producer + "】： 暂时不能执行生产任务!");
                try {
                    list.wait(); // 由于条件不满足，生产阻塞
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 生产产品
            list.add(new Object());
            System.out.println("【" + producer + "】：生产了一个产品\t【现仓储量为】:" + list.size());
            list.notifyAll();
        }
    }

    // 消费产品
    public void consume(String consumer) {
        synchronized (list) {
            //如果仓库存储量不足
            while (list.size() == 0) {
                System.out.println("仓库已空，【" + consumer + "】： 暂时不能执行消费任务!");
                try {
                    list.wait(); // 由于条件不满足，消费阻塞
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.remove();
            System.out.println("【" + consumer + "】：消费了一个产品\t【现仓储量为】:" + list.size());
            list.notifyAll();
        }
    }

    public LinkedList<Object> getList() {
        return list;
    }

    public int getMAX_SIZE() {
        return MAX_SIZE;
    }
}