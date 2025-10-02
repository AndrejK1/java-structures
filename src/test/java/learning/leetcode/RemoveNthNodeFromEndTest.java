package learning.leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * RemoveNthNodeFromEndTest.java
 *
 * @author Andrii Kononenko
 * @since 02.10.25
 */
class RemoveNthNodeFromEndTest {

    @Test
    void removeNthFromEnd() {
        runTestCase(List.of(1, 2, 3, 4, 5), 2, List.of(1, 2, 3, 5));
        runTestCase(List.of(1, 2, 3, 4, 5), 5, List.of(2, 3, 4, 5));
        runTestCase(List.of(1, 2), 2, List.of(2));
        runTestCase(List.of(1, 2), 1, List.of(1));
        runTestCase(List.of(1), 1, List.of());
    }

    private void runTestCase(List<Integer> input, int pos, List<Integer> expected) {
        RemoveNthNodeFromEnd.ListNode listNode = new RemoveNthNodeFromEnd().removeNthFromEnd(toListNode(input), pos);
        Assertions.assertEquals(expected, toList(listNode));
    }

    private RemoveNthNodeFromEnd.ListNode toListNode(List<Integer> input) {
        RemoveNthNodeFromEnd.ListNode head = new RemoveNthNodeFromEnd.ListNode(input.get(0));

        RemoveNthNodeFromEnd.ListNode current = head;

        for (int i = 1; i < input.size(); i++) {
            current.next = new RemoveNthNodeFromEnd.ListNode(input.get(i));
            current = current.next;
        }

        return head;
    }

    private List<Integer> toList(RemoveNthNodeFromEnd.ListNode head) {
        if (head == null) {
            return new ArrayList<>();
        }

        List<Integer> result = new ArrayList<>();

        do {
            result.add(head.val);
            head = head.next;
        } while (head != null);

        return result;
    }
}