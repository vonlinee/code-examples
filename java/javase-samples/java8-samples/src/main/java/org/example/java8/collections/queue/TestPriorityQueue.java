package org.example.java8.collections.queue;

import java.util.Comparator;
import java.util.PriorityQueue;

public class TestPriorityQueue {

    /**
     * 写成静态方便试验时操作
     */
    static Comparator<Test> cmp = new Comparator<Test>() {
        @Override
        public int compare(Test o1, Test o2) {
            return o1.index - o2.index;
        }
    };
    static PriorityQueue<Test> que = new PriorityQueue<>(cmp);

    public static void main(String[] args) {

        CreatElement();

        System.out.println("输出元素数量：" + que.size());
        System.out.println("是否包含：" + que.contains(que.peek()));
        System.out.println("是否包含：" + que.contains(new Test(11)));
        System.out.println("检索不弹出：" + que.peek().index);
        /**
         * 输出元素数量：10
         * 是否包含：true
         * 是否包含：false
         * 检索不弹出：0
         */
        while (!que.isEmpty()) {
            System.out.println("检索并弹出：" + que.poll().index);
        }
        System.out.println("检索并弹出，此时为空队列：" + que.poll());
        /**
         * 检索并弹出：0
         * 检索并弹出：1
         * 检索并弹出：2
         * 检索并弹出：3
         * 检索并弹出：4
         * 检索并弹出：5
         * 检索并弹出：6
         * 检索并弹出：7
         * 检索并弹出：8
         * 检索并弹出：9
         * 检索并弹出，此时为空队列：null
         */
        CreatElement();

        while (!que.isEmpty()) {
            System.out.println("移除并输出：" + que.remove().index);
        }
        /**
         * 移除并输出：0
         * 移除并输出：1
         * 移除并输出：2
         * 移除并输出：3
         * 移除并输出：4
         * 移除并输出：5
         * 移除并输出：6
         * 移除并输出：7
         * 移除并输出：8
         * 移除并输出：9
         */

    }

    /**
     * 添加元素的方法
     */
    public static void CreatElement() {
        for (int a = 0; a < 10; a++) {
            que.add(new Test(a));
        }
    }

}

class Test {
    public Test(int index) {
        this.index = index;
    }

    int index;
}