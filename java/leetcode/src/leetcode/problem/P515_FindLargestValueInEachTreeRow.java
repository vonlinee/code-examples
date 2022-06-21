package leetcode.problem;

import java.util.ArrayDeque;
import java.util.List;

/**
 * 
 * https://leetcode.cn/problems/find-largest-value-in-each-tree-row/
 * 给定一棵二叉树的根节点 root ，请找出该二叉树中每一层的最大值
 * 中等 created on 2022年6月20日
 */
public class P515_FindLargestValueInEachTreeRow {

	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode() {
		}

		TreeNode(int val) {
			this.val = val;
		}

		TreeNode(int val, TreeNode left, TreeNode right) {
			this.val = val;
			this.left = left;
			this.right = right;
		}
	}

	/**
	 * 解法1：层次遍历
	 * 如何判断是同一层
	 * @param root
	 * @return
	 */
    public List<Integer> largestValues(TreeNode root) {
    	ArrayDeque<TreeNode> queue = new ArrayDeque<>();
    	queue.add(root);
    	int max = root.val;
    	int level = 0;
    	int count = 0; // 辅助判断层数
    	while (!queue.isEmpty()) {
    		if (count == 0) {
				level ++;
			}
			TreeNode node = queue.poll();

			// 子节点入队
			if (node.left != null) {
				queue.add(node.left);
			}
			if (node.right != null) {
				queue.add(node.right);
			}
		}
		return null;
    }
}
