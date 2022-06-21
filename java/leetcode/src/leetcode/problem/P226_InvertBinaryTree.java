package leetcode.problem;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * https://leetcode.cn/problems/invert-binary-tree/
 * 
 * 翻转二叉树
 * 
 * created on 2022年6月19日
 */
public class P226_InvertBinaryTree {

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int val) {
			this.val = val;
		}

		TreeNode(int val, TreeNode left, TreeNode right) {
			this.val = val;
			this.left = left;
			this.right = right;
		}
	}

	// 测试通过
	public static TreeNode invertTree(TreeNode root) {
		if (root == null) {
			return root;
		}
		// 翻转当前节点
		TreeNode node = root.left;
		root.left = root.right;
		root.right = node;
		// 递归翻转子节点
		if (root.left != null) {
			invertTree(root.left);
		}
		if (root.right != null) {
			invertTree(root.right);
		}
		// 返回当前节点，即子树的根节点
		return root;
	}

	public TreeNode invertTree1(TreeNode root) {
		// LinkedList实现了集合框架的Queue接口
		Queue<TreeNode> q = new LinkedList<>();
		if (root != null)
			q.offer(root);// 加入元素
		while (!q.isEmpty()) {
			TreeNode curr = q.poll();// 获取并移出元素
			TreeNode tmp = curr.right;
			curr.right = curr.left;
			curr.left = tmp;
			if (curr.left != null)
				q.offer(curr.left);
			if (curr.right != null)
				q.offer(curr.right);
		}
		return root;
	}

	public TreeNode invertTree3(TreeNode root) {
		if (root == null) {
			return null;
		}
		Stack<TreeNode> stack = new Stack<>();
		stack.push(root);// 先将根节点压入堆栈，当前节点
		while (stack.size() > 0) {
			// 根据栈的先进后出操作，获取栈中最后一个元素，即最先入栈的元素
			TreeNode temp = stack.lastElement();
			stack.pop();// 弹出栈顶元素
			// 交换左右子树
			TreeNode tempLeft = temp.left;
			temp.left = temp.right;
			temp.right = tempLeft;
			// 左子树不为空，将左子树入栈
			if (temp.left != null) {
				stack.push(temp.left);
			}
			// 右子树不为空，将右子树入栈
			if (temp.right != null) {
				stack.push(temp.right);
			}
		}
		return root;
	}
}
