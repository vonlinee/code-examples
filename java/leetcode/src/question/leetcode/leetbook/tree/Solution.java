package question.leetcode.leetbook.tree;

public class Solution {

    // 求最大深度
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        // 每次返回结果时都会走完根节点到叶子节点之间的一条路径，即可得到该条路径的深度
        // 有点像贪心，每次递归都尝试取最大值
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    // 错误写法
    public boolean isValidBST1(TreeNode root) {
        // 空节点默认满足条件
        if (root == null) {
            return true;
        }
        int val = root.val;
        // 比较左节点
        boolean leftSmallThanHead = true;
        if (root.left != null) {
            leftSmallThanHead = root.left.val < val;
        }
        // 比较右节点
        boolean rightBigThanHead = true;
        if (root.right != null) {
            rightBigThanHead = root.right.val > val;
        }
        // 同时满足
        // 当前节点满足BST条件
        boolean flag = leftSmallThanHead && rightBigThanHead;
        // 向下递归
        return flag && isValidBST1(root.left) && isValidBST1(root.right);
    }

    public boolean isValidBST(TreeNode root) {
        return isValidBSTInRange(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public boolean isValidBSTInRange(TreeNode root, long min, long max) {
        if (root == null) return true;
        //每个节点如果超过这个范围，直接返回false
        if (root.val >= max || root.val <= min) return false;
        //这里再分别以左右两个子节点分别判断，
        //左子树范围的最小值是minVal，最大值是当前节点的值，也就是root的值，因为左子树的值要比当前节点小
        //右子数范围的最大值是maxVal，最小值是当前节点的值，也就是root的值，因为右子树的值要比当前节点大
        return isValidBSTInRange(root.left, min, root.val) && isValidBSTInRange(root.right, root.val, max);
    }

    public boolean isSymmetric(TreeNode root) {
        // 翻转左子树
        inverse(root.left);
        // 如果对称：反转过后的左子树和右子树应该是一样的
        return isSameTree(root.left, root.right);
    }

    public boolean isSameTree(TreeNode root1, TreeNode root2) {
        // 同时为null默认相等
        if (root1 == null && root2 == null) return true;
        boolean flag = true;
        if (root1 != null && root2 != null) {
            flag = root1.val == root2.val;
            // 比较左子节点
            return flag && isSameTree(root1.left, root2.left) && isSameTree(root1.right, root2.right);
        }
        // 其余情况不相等
        return false;
    }

    public void inverse(TreeNode root) {
        if (root != null) {
            TreeNode node = root.left;
            root.left = root.right;
            root.right = node;
            // 翻转子节点
            inverse(root.left);
            inverse(root.right);
        }
    }

    /**
     * https://leetcode.cn/leetbook/read/top-interview-questions-easy/xninbt/
     * @param nums
     * @return
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        return null;
    }

    public TreeNode arrayToBST(int[] nums) {
        TreeNode root = new TreeNode(nums[0]);
        for (int i = 1; i < nums.length; i++) {
            if (root.val > 1) return null;
        }
        return root;
    }

    public static void main(String[] args) {

    }
}