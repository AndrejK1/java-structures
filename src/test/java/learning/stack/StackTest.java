package learning.stack;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StackTest {

    @Test
    void testStack() {
        Stack<Integer> stack = new LinkedStack<>();

        Assertions.assertTrue(stack.isEmpty());

        stack.push(1);

        Assertions.assertFalse(stack.isEmpty());

        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);

        Assertions.assertEquals(5, (int) stack.pop());

        stack.pop();

        stack.push(6);
        stack.push(7);

        stack.pop();
        stack.pop();
        stack.pop();

        Assertions.assertEquals(2, (int) stack.pop());
        Assertions.assertEquals(1, (int) stack.pop());
        Assertions.assertTrue(stack.isEmpty());
    }
}
