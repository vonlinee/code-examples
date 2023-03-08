package sample.java8.newfeature.lambda;

import java.util.ArrayList;
import java.util.List;

/**
 * Lambda运行效率测试
 * https://blog.csdn.net/banzhuanhu/article/details/112277154
 */
public class LambdaTest1 {

    public static void main(String[] args) {

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(i);
        }
        long lambdaStart = System.currentTimeMillis();
        list.forEach(i -> {
            // 不用做事情，循环就够了
        });
        long lambdaEnd = System.currentTimeMillis();
        System.out.println("lambda循环运行毫秒数=" + (lambdaEnd - lambdaStart));


        long normalStart = System.currentTimeMillis();
        for (int i = 0; i < list.size(); i++) {
            // 不用做事情，循环就够了
        }
        long normalEnd = System.currentTimeMillis();
        System.out.println("普通循环运行毫秒数=" + (normalEnd - normalStart));

    }
}
