package leetcode.problem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 
 * https://leetcode-cn.com/problems/binary-tree-level-order-traversal
 * 
 * created on 2022年6月20日
 */
public class P102_BinaryTreeLevelOrderTraversal {

	/**
	 * 孩子兄弟表示法 created on 2022年6月20日
	 */
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
	 * 使用栈来对每层进行遍历，按层放入栈和取出
	 * @param root
	 * @return
	 */
	public List<List<Integer>> levelOrder(TreeNode root) {
		if (root == null) {
			return new ArrayList<>();
		}
		List<List<Integer>> res = new ArrayList<>();
		Queue<TreeNode> queue = new LinkedList<>();
		queue.add(root);
		while (!queue.isEmpty()) {
			int size = queue.size();// 当前层的节点数
			List<Integer> list = new ArrayList<>();
			while (size > 0) {
				// 取出数据
				TreeNode node = queue.poll();
				list.add(node.val);
				size--;
				// 下层的放入栈
				if (node.left != null) {
					queue.add(node.left);
				}
				if (node.right != null) {
					queue.add(node.right);
				}
			}
			res.add(list);
		}
		return res;
	}
}
