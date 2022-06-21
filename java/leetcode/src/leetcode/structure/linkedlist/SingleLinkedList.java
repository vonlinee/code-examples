package leetcode.structure.linkedlist;

/**
 * 单链表
 */
public class SingleLinkedList {

	// 头节点为空节点
	private Node head;
	
	public SingleLinkedList() {
		this.head = new Node(Integer.MIN_VALUE);
	}
	
	public static class Node {
		public int value;
		public Node next;

		public Node(int value) {
			super();
			this.value = value;
		}
	}

	// 要满足JVM可达性分析，不能导致头节点之后的被垃圾回收
	// 反转单链表
	public static Node reverse(Node head) {
		Node pre = null;
		Node next = null;
		while (head != null) {
			// 
			next = head.next;
			head.next = pre;
			pre = head;
			head = next; 
		}
		return pre;
	}
}
