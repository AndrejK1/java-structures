package learning.queue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class QueueTest {

    @Test
    void testQueue() {
        Queue<Integer> queue = new CycleArrayQueue<>(5, Integer.class);

        Assertions.assertTrue(queue.isEmpty());

        queue.push(1);

        Assertions.assertFalse(queue.isEmpty());

        queue.push(2);
        queue.push(3);
        queue.push(4);
        queue.push(5);

        Assertions.assertTrue(queue.isFull());

        Assertions.assertEquals(1, (int) queue.pop());

        Assertions.assertFalse(queue.isFull());

        queue.pop();

        queue.push(6);
        queue.push(7);

        Assertions.assertTrue(queue.isFull());

        queue.pop();

        Assertions.assertFalse(queue.isFull());

        queue.pop();
        queue.pop();

        Assertions.assertEquals(6, (int) queue.pop());
        Assertions.assertEquals(7, (int) queue.pop());
        Assertions.assertTrue(queue.isEmpty());
    }
}
