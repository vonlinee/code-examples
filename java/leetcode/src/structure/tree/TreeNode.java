package structure.tree;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 存放int值 树的结构是leetcode上所用的树的结构，二叉树结构
 * 孩子兄弟表示法
 * @since created on 2022年6月20日
 */
public class TreeNode {

	public int value;
	public TreeNode left;
	public TreeNode right;

	public TreeNode(int value) {
		super();
		this.value = value;
	}

	/**
	 * 创建树结构，具体查看：https://blog.csdn.net/seableble/article/details/103172601
	 * 
	 * @param nums 数组中的元素是按层次遍历的二叉树的值，如果子节点为空，那么数组元素对应为null
	 * @return 二叉树的根节点
	 */
	public static TreeNode create(Integer[] nums) {
		if (nums.length == 0)
			return new TreeNode(0);
		Deque<TreeNode> nodeQueue = new LinkedList<>();
		// 创建一个根节点
		TreeNode root = new TreeNode(nums[0]);
		nodeQueue.offer(root);
		TreeNode cur;
		// 记录当前行节点的数量（注意不一定是2的幂，而是上一行中非空节点的数量乘2）
		int lineNodeNum = 2;
		// 记录当前行中数字在数组中的开始位置
		int startIndex = 1;
		// 记录数组中剩余的元素的数量
		int restLength = nums.length - 1;
		while (restLength > 0) {
			// 只有最后一行可以不满，其余行必须是满的
//            // 若输入的数组的数量是错误的，直接跳出程序
//            if (restLength < lineNodeNum) {
//                System.out.println("Wrong Input!");
//                return new BinTreeNode(0);
//            }
			for (int i = startIndex; i < startIndex + lineNodeNum; i = i + 2) {
				// 说明已经将nums中的数字用完，此时应停止遍历，并可以直接返回root
				if (i == nums.length)
					return root;
				cur = nodeQueue.poll();
				if (nums[i] != null) {
					cur.left = new TreeNode(nums[i]);
					nodeQueue.offer(cur.left);
				}
				// 同上，说明已经将nums中的数字用完，此时应停止遍历，并可以直接返回root
				if (i + 1 == nums.length)
					return root;
				if (nums[i + 1] != null) {
					cur.right = new TreeNode(nums[i + 1]);
					nodeQueue.offer(cur.right);
				}
			}
			startIndex += lineNodeNum;
			restLength -= lineNodeNum;
			lineNodeNum = nodeQueue.size() * 2;
		}

		return root;
	}

	/**
	 * 前序遍历此二叉树
	 * 
	 * @param root
	 */
	public static void preOrder(TreeNode root) {
		if (root == null)
			return;
		System.out.print(root.value + " ");
		preOrder(root.left);
		preOrder(root.right);
	}

	/**
	 * 层次遍历
	 * 
	 * @param root
	 */
	public static void levelOrder(TreeNode root) {

	}

	/**
	 * 层次遍历，使用队列
	 * @param root
	 * @return
	 */
	public static List<Integer> levelOrderWithQueue(TreeNode root) {
		// 创建一个类型为TreeNode 的队列， 另外创建一个类型为Integer的ArrayList用于存放结果
		Queue<TreeNode> q = new LinkedList<>();
		List<Integer> res = new ArrayList<>();
		if (root == null)
			return res; // 注意此处的res是一个空数组
		q.offer(root); // q.offer() 是队列的一个方法，表示向队列尾部添加元素
		// 入队顺序：当前节点入队 -> 左节点入队 -> 右节点入队
		// 出队顺序：当前节点出队 -> 左节点出队 -> 右节点出队
		while (!q.isEmpty()) {
			TreeNode cur = q.poll(); // q.poll()表示从队列头部删除数据
			res.add(cur.value); // q.add() 也是队列的一个方法，表示向队列尾部添加元素
			if (cur.left != null) {
				q.offer(cur.left);
			}
			if (cur.right != null) {
				q.offer(cur.right);
			}
		}
		return res;
	}

	/*
	 * 树的结构示例： 
	 * 				1 
	 * 			  /   \ 
	 *           2     3 
	 *         /   \   / \ 
	 *        4     5  6   7
	 */

	// 用于获得树的层数
	public static int getTreeDepth(TreeNode root) {
		return root == null ? 0 : (1 + Math.max(getTreeDepth(root.left), getTreeDepth(root.right)));
	}

	/**
	 * 参考：https://blog.csdn.net/lenfranky/article/details/89645755
	 */
	public static void printTree() {

	}

	private static void writeArray(TreeNode currNode, int rowIndex, int columnIndex, String[][] res, int treeDepth) {
		// 保证输入的树不为空
		if (currNode == null)
			return;
		// 先将当前节点保存到二维数组中
		res[rowIndex][columnIndex] = String.valueOf(currNode.value);
		// 计算当前位于树的第几层
		int currLevel = ((rowIndex + 1) / 2);
		// 若到了最后一层，则返回
		if (currLevel == treeDepth)
			return;
		// 计算当前行到下一行，每个元素之间的间隔（下一行的列索引与当前元素的列索引之间的间隔）
		int gap = treeDepth - currLevel - 1;
		// 对左儿子进行判断，若有左儿子，则记录相应的"/"与左儿子的值
		if (currNode.left != null) {
			res[rowIndex + 1][columnIndex - gap] = "/";
			writeArray(currNode.left, rowIndex + 2, columnIndex - gap * 2, res, treeDepth);
		}
		// 对右儿子进行判断，若有右儿子，则记录相应的"\"与右儿子的值
		if (currNode.right != null) {
			res[rowIndex + 1][columnIndex + gap] = "\\";
			writeArray(currNode.right, rowIndex + 2, columnIndex + gap * 2, res, treeDepth);
		}
	}

	/**
	 * 本方法的思路是基于字符串的数组的，所以并不可能完美适配所有情况，比如当树的高度很高以后，可能看起来会很奇怪（?）
	 * 然已经做了自适应处理，但是，如果出现超过5位的数字（比如123123），其所在的行可能会有一点向右的偏移，
	 * 若偏的不多，是不影响观察的，但若偏的多了就。。。。不过这里已经做了处理，所以出现三位或者四位数的时候是没有问题的。
	 * 
	 * @param root
	 */
	public static void show(TreeNode root) {
		if (root == null)
			System.out.println("EMPTY!");
		// 得到树的深度
		int treeDepth = getTreeDepth(root);
		// 最后一行的宽度为2的（n - 1）次方乘3，再加1
		// 作为整个二维数组的宽度
		int arrayHeight = treeDepth * 2 - 1;
		int arrayWidth = (2 << (treeDepth - 2)) * 3 + 1;
		// 用一个字符串数组来存储每个位置应显示的元素
		String[][] res = new String[arrayHeight][arrayWidth];
		// 对数组进行初始化，默认为一个空格
		for (int i = 0; i < arrayHeight; i++) {
			for (int j = 0; j < arrayWidth; j++) {
				res[i][j] = " ";
			}
		}
		// 从根节点开始，递归处理整个树
		// res[0][(arrayWidth + 1)/ 2] = (char)(root.val + '0');
		writeArray(root, 0, arrayWidth / 2, res, treeDepth);

		// 此时，已经将所有需要显示的元素储存到了二维数组中，将其拼接并打印即可
		for (String[] line : res) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < line.length; i++) {
				sb.append(line[i]);
				if (line[i].length() > 1 && i <= line.length - 1) {
					i += line[i].length() > 4 ? 2 : line[i].length() - 1;
				}
			}
			System.out.println(sb.toString());
		}
	}

	// 下面是测试案例

	public static void test1() {
		// 根据给定的数组创建一棵树
		TreeNode root = create(new Integer[] { 1, 2, 3, 4, 5, null, null, 6, 7 });
		// 将刚刚创建的树打印出来
		show(root);
		
		List<Integer> elements = levelOrderWithQueue(root);
		System.out.println(elements);
	}

	public static void test2() {
		Integer[] nums = { 5, 4, 8, 11, null, 13, 4, 7, 2, null, null, null, 1 };
		TreeNode root = create(nums);
		// preOrder(root); // 5 4 11 7 2 8 13 4 1
		show(root);
	}

	public static void main(String[] args) {
		// long start = System.currentTimeMillis();
		test1();
		// long end = System.currentTimeMillis();
		// System.out.println(end - start + " ms");
	}
}
