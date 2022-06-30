package nowcoder.hj;

/**
 * <p>
 * https://blog.nowcoder.net/n/9e9728aa60ea42ab92b472a4982d9483
 * </p>
 * 个人感觉比较难
 */
public class HJ6_FindIt {

    static int x, y;

    public static void main(String[] args) {
        System.out.println("abcdefg".indexOf("efg"));
    }

    // 题目理解：不只是从左到右

    // 【找到它】
    // 找到它是一个小游戏，你需要在一个矩阵中找到给定的单词。假设给定单词 HELLOWORD， 在矩阵中只要能找到 H->E->L->L->O->W->O->R->L->D连成
    // 的单词，就算通过。注意区分英文字母大小写，并且您只能上下左右行走，不能走回头路

    /**
     * 将字符矩阵转化为字符串，问题转化为求字符串中子字符串的起始位置
     * @param parent
     * @param child
     */
    public static void findIt(String parent, String child) {
        int i = parent.indexOf(child);
    }
}
