package question.leetcode.leetbook;

public class P2 {

    public boolean isPalindrome(ListNode head) {
        ListNode current = head;
        // 统计链表节点个数
        int count = 0;
        while (current != null) {
            count++;
            current = current.next;
        }
        // 遍历链表，一分为二

        current = head;
        int i = 0;
        ListNode head2 = null;
        while (current != null) {
            i++;
            if (i == count / 2) {
                if (count % 2 == 0) {
                    head2 = current.next;
                } else {
                    // 奇数个节点，头节点
                    head2 = current.next.next;
                }
                current.next = null;
                break;
            }
            current = current.next;
        }
        // 拆成head和head2两个链表
        // 将head这个反转一下
        head = reverseList(head);
        // 遍历链表，对应元素是否相等
        ListNode current1 = head;
        ListNode current2 = head2;
        while (current1 != null && current2 != null) {
            if (current1.val != current2.val) return false;
            current1 = current1.next;
            current2 = current2.next;
        }
        return true;
    }

    public ListNode reverseList(ListNode head) {
        //终止条件
        if (head == null || head.next == null) return head;
        //保存当前节点的下一个结点
        ListNode next = head.next;
        //从当前节点的下一个结点开始递归调用
        ListNode reverse = reverseList(next);
        //reverse是反转之后的链表，因为函数reverseList
        // 表示的是对链表的反转，所以反转完之后next肯定
        // 是链表reverse的尾结点，然后我们再把当前节点
        //head挂到next节点的后面就完成了链表的反转。
        next.next = head;
        //这里head相当于变成了尾结点，尾结点都是为空的，
        //否则会构成环
        head.next = null;
        return reverse;
    }

    public boolean hasCycle(ListNode head) {
        ListNode current = head;
        while (current != null) {
            // 以当前节点开始
            // 遍历当前节点作为头节点的一个子链表
            ListNode cur = current.next;
            while (cur != null) {
                if (cur == current) {
                    // 如果有环
                    return true;
                }
                cur = cur.next;
            }
            current = current.next;
        }
        return false;
    }

    public static void main(String[] args) {
        int[] arr = {3, 2, 0, -4};
        ListNode head = createLinkedList(arr);
        P2 p = new P2();
        boolean b = p.hasCycle(head);
        System.out.println(b);
    }

    public static ListNode createLinkedList(int[] arr) {
        // 这三个指针是必须的
        // 头节点
        ListNode head = null;
        // 用于遍历的指针，用于连接创建的节点，最终prev指向尾节点
        ListNode prev = null;
        // 当前节点
        ListNode node;
        for (int i = 0; i < arr.length; i++) {
            // 本次循环这个指针是不可变的，因此需要prev这个指针
            node = new ListNode(arr[i]);
            if (prev != null) {
                prev.next = node;
                // 让前一个节点指向当前节点，从而继续向后进行
                prev = node;
            }
            // 只执行一次
            if (head == null) {
                head = node;
                prev = head;
            }
        }
        return head;
    }
}

class ListNode {
    int val;
    ListNode next;

    ListNode() {}

    ListNode(int val) {this.val = val;}

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}