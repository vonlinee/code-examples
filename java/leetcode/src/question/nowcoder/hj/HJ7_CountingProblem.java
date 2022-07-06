package question.nowcoder.hj;

import java.util.List;

/**
 * 【报数问题】有n个人围成一圈，顺序排号为1-n，从第1个人开始报数(从1到3报数)，凡报到3的人退出圈子，问最后留下的是原来第几号的那位。
 * 输入描述:
 * 输入人数n(n<1000)
 * 输出描述:
 * 输出最后留下来的是原来第几号
 */
public class HJ7_CountingProblem {

    public static void main(String[] args) {
        int N = 2;
        int[] nums = new int[N];
        for (int i = 0; i < N; i++) {
            nums[i] = i + 1;
        }
        counting(nums);
    }

    public static void counting(int[] nums) {
        int count = 0; // 从1-3报数
        int left = nums.length; // 记录退出圈的人数
        // 大于2时表示还有人会数到3从而退出圈子
        while (left != 1) {
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] == 0) {
                    continue;
                }
                count++;
                if (count == 3) {
                    nums[i] = 0; // 报到3的人退出
                    count = 0; // 计数器置为0
                    left--;
                }
            }
        }
        // 输出结果
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                System.out.print(nums[i] + " ");
            }
        }
    }

    public static void counting1(List<Integer> nums) {
        int count = 0;
        int i = 0;
        while (nums.size() >= 1) {
            count++;
            if (count == 3) {
                nums.remove(i);
                count = 0;
            } else {
                i++;
            }
            if (i > nums.size() - 1) {
                i = 0;
            }
        }
    }
}
