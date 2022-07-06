package structure.linkedlist;

public class DoubleLinkedList {

	public static class Node {

		public int value;
		public Node last; // 前驱节点
		public Node next; // 后继节点

		public Node(int value) {
			super();
			this.value = value;
		}
	}
}
