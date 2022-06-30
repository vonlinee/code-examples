package nowcoder.hj;

import java.util.Arrays;

/**
 * https://blog.nowcoder.net/n/e4d9a9d482c34f0a9b48e68fdc4c5746
 */
public class HJ15_ContinuousSequence {

    public static void main(String[] args) {
        play1(525, 6);
    }

    /**
     * 已知连续正整数数列{K}=K1,K2,K3...Ki的各个数相加之和为S，i=N(0<S<100000,0<N<100000),求此数列K。
     * 输入描述:
     * 输入包含两个参数，1)连续正整数数列和S，2)数列里数的个数N。
     * 输出描述:
     * 如果有解输出数列K，如果无解输出-1。
     * @param S
     * @param N
     */
    public static void play(int S, int N) {
        int[] k = new int[N];
        int max = S / N;
        if (N % 2 == 0) { // 偶数个
            // 找到中位数
            int group = N / 2;
            // 中位数的下标，满足 (k[i] + k[j]) * group = S
            int j = N / 2, i = N / 2 - 1;
            System.out.println("中位数" + i + " " + j);
            int value = 0;
            while (value <= S) {
                value++;
                if ((2 * value + 1) * group == S) {
                    System.out.println("找到value = " + value);
                    break;
                }
            }
            k[i] = value;
            k[j] = value + 1;
            // 填充数组
            for (int l = 0; l < i; l++) {
                k[l] = k[i] - i + l;
            }
            for (int l = j + 1; l < N; l++) {
                k[l] = k[j] + l - j;
            }
        } else {
            // 找到中位数
            int group = N / 2;

        }
        // 暴力求解
        System.out.println(Arrays.toString(k));

    }

    // 理解：这里指出是连续正整数数列，就比较好做了

    public static void play1(int S, int N) {
        int[] K = new int[N];
        int middle = 0;
        if (N % 2 == 0) {
            middle = (int) (S / N + 0.5);
        } else {
            middle = S / N;
        }
        // 注意这里的计算方法
        int start = (int) (middle - Math.floor((double) N / 2)) + 1;
        for (int i = 0; i < K.length; i++) {
            K[i] = start + i;
        }
        if (K[0] <= 0) {
            System.out.println("-1");
        }
        System.out.println(Arrays.toString(K));
    }
}
