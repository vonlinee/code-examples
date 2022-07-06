package question.leetcode.problem;

import java.util.Deque;
import java.util.Stack;

public class P1047_RemoveDuplicates {

    public static void main(String[] args) {
        P1047_RemoveDuplicates test = new P1047_RemoveDuplicates();

        String abbaca = test.removeDuplicates("abbaca");
        Deque<String> deque;
        System.out.println(abbaca);
    }

    public String removeDuplicates(String s) {
        Stack<Character> stack = new Stack<>();
        // 第一个肯定入栈
        stack.push(s.charAt(0));
        for (int i = 1; i < s.length(); i++) {
            // 栈为空时肯定要压栈
            if (stack.isEmpty()) {
                stack.push(s.charAt(i));
                continue;
            }
            // 相邻的重复字符，peek的作用是查看栈顶元素，但不弹出
            if (s.charAt(i) == stack.peek()) {
                stack.pop();
                continue;
            }
            // 压栈
            stack.push(s.charAt(i));
        }
        StringBuilder result = new StringBuilder();
        for (Character character : stack) {
            result.append(character);
        }
        return result.toString();
    }

}
