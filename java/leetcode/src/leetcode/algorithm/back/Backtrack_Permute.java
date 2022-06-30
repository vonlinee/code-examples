package leetcode.algorithm.back;

import java.util.LinkedList;
import java.util.List;

public class Backtrack_Permute {

    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new LinkedList<>();
        LinkedList<Integer> list = new LinkedList<>();
        backtrack(nums, list, result);
        return result;
    }

    public static void backtrack(int[] nums, LinkedList<Integer> track, List<List<Integer>> result) {
        // 到达叶子节点，将路径装入结果列表里
        if (track.size() == nums.length) {
            result.add(new LinkedList<>(track));
        }
        for (int i = 0; i < nums.length; i++) {
            // 排除不合法的选择
            if (track.contains(nums[i])) {
                continue;
            }
            // 做选择
            track.add(nums[i]);
            // 进入下一层决策树
            backtrack(nums, track, result);
            // 取消选择
            track.removeLast();
        }
    }
}
