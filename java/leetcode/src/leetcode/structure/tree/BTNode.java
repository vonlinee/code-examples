package leetcode.structure.tree;

/**
 * 
 * 通用的二叉树节点 
 * created on 2022年6月20日
 */
public class BTNode<T> {

	public T value;
	public BTNode<T> left;
	public BTNode<T> right;

	public BTNode(T value) {
		super();
		this.value = value;
	}
}
