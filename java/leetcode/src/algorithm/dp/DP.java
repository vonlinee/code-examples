package algorithm.dp;

public class DP {

    public static void main(String[] args) {

    }

    public static int fibonacci(int N) {
        if (N == 0) return 0;
        int[] dp = new int[N + 1];
        //
        dp[0] = 0;
        dp[1] = 1;
        // 状态转移
        for (int i = 2; i <= N; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[N];
    }

    public static int fibonacci1(int N) {
        if (N == 0 || N == 1) return N;
        // 递推关系
        int prev = 0, curr = 1;
        // 迭代求解
        for (int i = 2; i <= N; i++) {
            int sum = prev + curr;
            prev = curr;
            curr = sum;
        }
        return curr;
    }
}
