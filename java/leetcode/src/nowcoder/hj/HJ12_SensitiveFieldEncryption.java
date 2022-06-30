package nowcoder.hj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://blog.nowcoder.net/n/68b689c14fe14c8d90ea4a84fb32f712
 */
public class HJ12_SensitiveFieldEncryption {

    public static void main(String[] args) {
        String play = play();
        System.out.println(play);
    }

    public static String play() {
        String string = "pasword_a12345678__timeout_100";
        int index = 1;
        String[] words = string.split("_");
        System.out.println(Arrays.toString(words));
        // 去掉空格字符串
        List<String> list = new ArrayList<>(Arrays.asList(words));
        list.remove("");
        if (index * 2 <= list.size()) {
            list.set(index * 2 - 1, "*");
        } else return "ERROR";
        return String.join("_", list);
    }
}
