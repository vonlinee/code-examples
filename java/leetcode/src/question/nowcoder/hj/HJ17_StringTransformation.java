package question.nowcoder.hj;

/**
 * https://blog.nowcoder.net/n/bfbf32f487c44127bee8e059fd0f37db
 */
public class HJ17_StringTransformation {

    public static void main(String[] args) {

        // 字典序：也就是字母排序
        play("bcfdefa".toCharArray());
    }

    // 比较： abc是要小于aac的
    // bac > aac

//    abcdef

    // 此解法有错
    public static void play(char[] chars) {
        // 我理解这就是个选择排序法，找出最小的字符，替换到尽可能靠前的位置，只替换一次
        //
        int min_index = 0;
        for (int i = 0; i < chars.length; i++) {
            min_index = i;
            for (int j = 0; j < chars.length; j++) {
                if (chars[j] < chars[min_index]) {
                    min_index = j;
                }
            }
            if (min_index != i) {
                char temp = chars[min_index];
                chars[min_index] = chars[i];
                chars[i] = temp;
            }
        }
        System.out.println(String.valueOf(chars));
    }

    public static void play1() {

    }
}
