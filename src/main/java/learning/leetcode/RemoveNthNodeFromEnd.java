package learning.leetcode;

/**
 * RemoveNthNodeFromEnd.java
 *
 * @author Andrii Kononenko
 * @since 02.10.25
 */
public class RemoveNthNodeFromEnd {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (n == 1 && (head == null || head.next == null)) {
            return null;
        }

        int currentIdx = 0;
        ListNode nodeToReassign = head;
        ListNode currentNode = head;

        while (currentNode.next != null) {
            currentNode = currentNode.next;

            if (n <= currentIdx) {
                nodeToReassign = nodeToReassign.next;
            }

            currentIdx++;
        }

        if (currentIdx > 0 && n == currentIdx + 1) {
            return head.next;
        }

        nodeToReassign.next = nodeToReassign.next.next;

        return head;
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

}
