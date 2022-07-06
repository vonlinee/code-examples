package question.nowcoder.hj;

/**
 * https://blog.nowcoder.net/n/8670301651be4d26bbd6be9789dcc2d3
 */
public class HJ16_BasketBallGame {

    public static void main(String[] args) {

    }

    // 动态规划求解

    public static void basketball(int[] arr) {
        // 总战力
        int total = sum(arr);

        // 第一队
        int[] team1 = new int[arr.length / 2];
    }

    public static int sum(int[] arr) {
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        return sum;
    }
}
