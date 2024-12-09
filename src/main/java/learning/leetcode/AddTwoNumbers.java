package learning.leetcode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AddTwoNumbers {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode result = null;
        ListNode lastNode = null;
        int total = 0;

        while (l1 != null || l2 != null) {
            if (l1 != null) {
                total += l1.val;
            }

            if (l2 != null) {
                total += l2.val;
            }

            ListNode newNode = new ListNode(total % 10);

            if (result == null) {
                result = newNode;
                lastNode = result;
            } else {
                lastNode.next = newNode;
                lastNode = newNode;
            }

            if (l1 != null) {
                l1 = l1.next;
            }

            if (l2 != null) {
                l2 = l2.next;
            }

            total = total / 10;
        }

        if (total > 0) {
            lastNode.next = new ListNode(1);
        }

        return result;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class ListNode {
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }

}
