package mybatis.tree;

public class Main {

    public static void main(String[] args) {

        TreeNode node = new TreeNode("A");

        TreeNode node1 = new TreeNode("A1");
        TreeNode node2 = new TreeNode("A2");

        node.addChild(node1, node2);

        System.out.println(node);
    }
}
