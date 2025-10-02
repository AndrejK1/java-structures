package learning.leetcode;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * MergeLists.java
 *
 * @author Andrii Kononenko
 * @since 02.10.25
 */
public class MergeLists {
    public ListNode mergeKListsV2(ListNode[] lists) {
        PriorityQueue<ListNode> pq = new PriorityQueue<>(Comparator.comparingInt(node -> node.val));

        for (ListNode node : lists) {
            while (node != null) {
                pq.add(node);
                node = node.next;
            }
        }

        ListNode head = pq.poll();
        ListNode tail = head;

        while (!pq.isEmpty()) {
            tail.next = pq.poll();
            tail = tail.next;
        }

        if (tail != null) {
            tail.next = null;
        }

        return head;
    }

    public ListNode mergeKLists(ListNode[] lists) {
        ListNode result = null;
        ListNode last = null;
        ListNode min;
        int pos;

        while (true) {
            min = null;
            pos = -1;

            for (int i = 0; i < lists.length; i++) {
                if (lists[i] == null) {
                    continue;
                }

                if (min == null) {
                    min = lists[i];
                    pos = i;
                } else if (lists[i].val < min.val) {
                    min = lists[i];
                    pos = i;
                }
            }

            if (min == null) {
                break;
            }

            lists[pos] = lists[pos].next;

            if (result == null) {
                result = min;
            } else {
                last.next = min;
            }

            last = min;
        }

        if (last != null) {
            last.next = null;
        }

        return result;
    }

    public class ListNode {
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
