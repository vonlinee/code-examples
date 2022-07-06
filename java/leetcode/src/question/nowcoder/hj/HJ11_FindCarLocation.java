package question.nowcoder.hj;

/**
 * https://blog.nowcoder.net/n/3b5e67f31f624b48aea61fe8a85b5777
 */
public class HJ11_FindCarLocation {

    public static void main(String[] args) {
        String str = "1,0,0,0,0";

        String[] split = str.split(",");
        char[] arr = new char[split.length];
        for (int i = 0; i < split.length; i++) {
            arr[i] = split[i].charAt(0);
        }
        find1(arr);
    }

    // 找到一个车位，使得距
    // 停车人的车最近的车辆的距离是最大的，返回此次的最大距离
    public static void find(char[] arr) {
        // 0为空位，1为已停车
        // 记录本次找到的停车位
        int localtion = 0;
        // 记录左边和右边最近的车辆的位置
        int left = -1, right = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == '0') {
                // 空位
                localtion = i;
                continue;
            }
            // 主要涉及到对左右边界的赋值问题
            if (arr[i] == '1') { // 说明这里不能停，那么肯定在右边
                if (right == -1) {
                    left = i;
                }
                right = i;
            }
            // 确定一个位置
            if (left >= 0 && right >= 0) {

            }
        }
    }

    public static void find1(char[] arr) {
        int max = 0;
        int left = 0, right = 0;
        for (int i = 0; i < arr.length; i++) {
            // 从第一个0开始依次向后找第一个1位置
            if (arr[i] == '0') {
                left = 0;
                right = 0;
                // 循环找到左边的最大距离
                int j = i - 1;
                while (j >= 0) {
                    left = i - j;
                    // 直到找到左边最近的一个车
                    if (arr[j] == '1') {
                        break;
                    }
                    j--;
                }
                // 循环找到右边的最大距离
                int k = i + 1;
                while (k <= arr.length - 1) {
                    right = k - i;
                    if (arr[k] == '1') {
                        break;
                    }
                    k++;
                }
                max = Math.max(max, Math.min(left, right));
            }
        }
        System.out.println(max);
    }

}
