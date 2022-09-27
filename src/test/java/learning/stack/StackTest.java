package learning.stack;

import org.junit.Assert;
import org.junit.Test;

public class StackTest {

    @Test
    public void testStack() {
        Stack<Integer> stack = new LinkedStack<>();

        Assert.assertTrue(stack.isEmpty());

        stack.push(1);

        Assert.assertFalse(stack.isEmpty());

        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);

        Assert.assertEquals(5, (int) stack.pop());

        stack.pop();

        stack.push(6);
        stack.push(7);

        stack.pop();
        stack.pop();
        stack.pop();

        Assert.assertEquals(2, (int) stack.pop());
        Assert.assertEquals(1, (int) stack.pop());
        Assert.assertTrue(stack.isEmpty());
    }
}
