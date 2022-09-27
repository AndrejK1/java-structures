package learning.queue;

import org.junit.Assert;
import org.junit.Test;

public class QueueTest {

    @Test
    public void testQueue() {
        Queue<Integer> queue = new CycleArrayQueue<>(5, Integer.class);

        Assert.assertTrue(queue.isEmpty());

        queue.push(1);

        Assert.assertFalse(queue.isEmpty());

        queue.push(2);
        queue.push(3);
        queue.push(4);
        queue.push(5);

        Assert.assertTrue(queue.isFull());

        Assert.assertEquals(1, (int) queue.pop());

        Assert.assertFalse(queue.isFull());

        queue.pop();

        queue.push(6);
        queue.push(7);

        Assert.assertTrue(queue.isFull());

        queue.pop();

        Assert.assertFalse(queue.isFull());

        queue.pop();
        queue.pop();

        Assert.assertEquals(6, (int) queue.pop());
        Assert.assertEquals(7, (int) queue.pop());
        Assert.assertTrue(queue.isEmpty());
    }
}
