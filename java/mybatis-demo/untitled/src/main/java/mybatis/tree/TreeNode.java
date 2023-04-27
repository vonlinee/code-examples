package mybatis.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 每个节点都会携带一个值
 */
public class TreeNode {

    private Object value;

    /**
     * 父节点
     */
    private TreeNode parent;

    /**
     * 每个节点的值都可以不同
     */
    private List<TreeNode> children;

    public TreeNode(Object value) {
        this.value = value;
    }

    public TreeNode(Object value, TreeNode parent) {
        this.value = value;
        this.parent = parent;
    }

    public List<TreeNode> getChildren() {
        if (children == null) {
            children = new ArrayList<>();
        }
        return children;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public TreeNode getParent() {
        return parent;
    }

    /**
     * 获取此节点的值
     * @param <V> 期望的值类型
     * @return 当前Node的值
     * @throws ClassCastException 类型转换错误
     */
    @SuppressWarnings("unchecked")
    public <V> V getValue() {
        return (V) value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void addChild(TreeNode... children) {
        final List<TreeNode> childrenNodes = getChildren();
        for (TreeNode child : children) {
            child.setParent(this);
            childrenNodes.add(child);
        }
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
}
