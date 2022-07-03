package leetcode.primary;

import leetcode.algorithm.SortAlgorithm;

import static leetcode.algorithm.SortAlgorithm.printArray;

/**
 * https://www.bilibili.com/video/BV1Zr4y1W7ww?p=13
 * <p>
 * 随机数生成，生成随机数组
 */
public class MathRandom {

    // 不同语言有不同随机函数
    public static void test1() {
        double random = Math.random(); // 范围[0, 1)，左闭右开，而且是等概率返回一个值
        // 计算机里的数是有精度的，因此计算机里的数不是无穷的，而是离散的有限值
        double expectedValue = 0.3d;

        int testTimes = 1000000;
        int count = 0;
        for (int i = 0; i < testTimes; i++) {
            if (Math.random() < expectedValue) {
                count++;
            }
        }

        // 计算概率
        System.out.println((double) count / (double) testTimes);

        System.out.println("===================");

        // 在[0, N)上仍然等概率返回
        count = 0;
        expectedValue = 4;
        double N = 8;
        for (int i = 0; i < testTimes; i++) {
            if (Math.random() * N < expectedValue) {
                count++;
            }
        }
        // 概率为 expectedValue / N
        System.out.println((double) count / (double) testTimes);// 小于4的概率接近50%

        System.out.println("===================");

        // 结论：Math.random()返回值在[0,x)的概率为x
        N = 9;

        int counts[] = new int[9];

        for (int i = 0; i < testTimes; i++) {
            int answer = (int) (Math.random() * N);
            counts[answer]++;
        }
        // 每种数出现的概率
        for (int i = 0; i < counts.length; i++) {
            System.out.println(i + "这个数出现了" + counts[i] + "次！");
        }

        System.out.println("=========================");
        count = 0;
        double x = 0.17;
        for (int i = 0; i < testTimes; i++) {
            if (xToXPower2() < x) {
                count++;
            }
        }
        System.out.println((double) count / (double) testTimes);// 小于4的概率接近50%
        System.out.println(Math.pow((double) x, 2));// 小于4的概率接近50%

        System.out.println((double) 1 - Math.pow((double) 1 - x, 2));

        System.out.println("===========================");

        System.out.println(f1());
    }

    /**
     * 返回[0, 1)的一个小数
     * 任意的x，x属于[0, 1), [0,x)上的数出现概率由原来的x调整为x的平方
     * TODO 分析用Math.min的概率 = 1-(1-x)^2
     * @return
     */
    public static double xToXPower2() {
        // 两次随机行为：只有两次都在[0,x)范围，最终结果才会在[0,x)范围
        // 由概率论可知：最终的概率为第一个数在[0,x)范围的概率（x）乘以第二个数在[0,x)范围的概率（x）= x平方
        return Math.max(Math.random(), Math.random());
    }

    // 概率论求分布函数

    /**
     * 返回[0, 1)的一个小数
     * 任意的x，x属于[0, 1), [0,x)上的数出现概率由原来的x调整为x的平方
     * TODO 分析用Math.min的概率 = 1-(1-x)^2
     * @return
     */
    public static double xToXPower3() {
        // 两次随机行为：只有两次都在[0,x)范围，最终结果才会在[0,x)范围
        // 由概率论可知：最终的概率为第一个数在[0,x)范围的概率（x）乘以第二个数在[0,x)范围的概率（x）= x平方
        return Math.min(Math.random(), Math.random());
    }

    // 随机等概率返回[1, 5]
    // 无法改变
    public static int f1() {
        // 实际上是[1, 6)，因为转int丢失了小数，因此是[1, 5]
        return (int) (Math.random() * 5) + 1;
    }

    // 随机机制，只能用0/1
    // 等概率返回0和1
    public static int f2() {
        int ans = 0;
        do {
            ans = f1(); // 要求f1函数必须是等概率的
        } while (ans == 3); // =3的概率会分成四等份，平摊到其他的概率上
        //
        return ans < 3 ? 0 : 1;
    }

    // 得到000 - 111（即0-7），做到等概率，转换成了二进制
    public static int f3() {
        int ans = (f2() << 2) + (f2() << 1) + (f2() << 0);
        return ans;
    }

    // 等概率返回[0, 6]
    public static int f4() {
        int ans = 0;
        do {
            ans = f3();
        } while (ans == 7);
        return ans;
    }

    public static int g() {
        return f4() + 1;
    }

    // 黑盒
    // 固定概率返回0和1，但是0和1的概率是不等的，且未知
    public static int x() {
        return Math.random() < 0.84 ? 0 : 1;
    }

    // 两次执行x这个随机事件只有四种情况：
    // p1 = 0, p2 = 0
    // p1 = 0, p2 = 1
    // p1 = 1, p2 = 0
    // p1 = 1, p2 = 1
    // 等概率产生0和1
    public static int y() {
        int ans = 0;
        do {
            ans = x();
        } while (ans == x()); // 第一次等于第二次，则重新进行
        // 只有下面两种情况：会返回，因此y函数的结果只有0和1，且概率相等的
        // p1 = 0, p2 = 1
        // p1 = 1, p2 = 0
        return ans;
    }

    // 随机数组：长度随机，值随机
    public static int[] randomIntArray(int maxLen, int maxValue) {
        if (maxLen < 1) {
            maxLen = 1;
        }
        // 长度随机
        int len = (int) (Math.random() * maxLen);
        int arr[] = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * maxValue);
        }
        return arr;
    }

    // 拷贝数组
    public static int[] copyArray(final int[] arr) {
        int newArr[] = new int[arr.length];
        for (int i = 0; i < newArr.length; i++) {
            newArr[i] = arr[i];
        }
        return newArr;
    }

    // 保证输入参数是等长的
    // 验证两个数组是否值相等
    public static boolean arrayEquals(final int[] arr1, final int[] arr2) {
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    // 是否是升序
    public static boolean isAscSorted(int[] arr) {
        if (arr.length < 2) {
            return true;
        }
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (max > arr[i]) {
                return false;
            }
            max = Math.max(max, arr[i]);
        }
        return true;
    }

    // 比较器并不是通用的，类似于单元测试，需要针对不同的测试用例写不同的比较器

    // 比较器，大样本进行测试，大样本随机都对，那么某个肯定对
    // 此函数主要用于测试
    public static void test5() {
        int maxLen = 5;
        int maxValue = 10;
        int testTimes = 100000;

        for (int i = 0; i < testTimes; i++) {
            int[] arr1 = randomIntArray(maxLen, maxValue);
            int[] arr2 = copyArray(arr1);
            SortAlgorithm.selectSort(arr1);
            // SortAlgorithm.insertSort2(arr2);
            if (!isAscSorted(arr1)) {
                System.out.println("选择排序错了");
                printArray(arr2);
                break;
            }
        }
    }
}
