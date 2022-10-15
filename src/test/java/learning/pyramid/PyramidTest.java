package learning.pyramid;

import learning.queue.PriorityQueue;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class PyramidTest {
    private final Random r = new Random();

    @Test
    public void testPyramid() {
        int defaultPriority = 100;

        PriorityQueue<Integer> queue = new Pyramid<>(5, defaultPriority);

        Assert.assertTrue(queue.isEmpty());

        Stream.generate(() -> r.nextInt(defaultPriority * 2))
                .limit(100)
                .forEach(i -> queue.push(i, i));

        queue.push(defaultPriority);

        List<Integer> elements = new ArrayList<>();

        while (!queue.isEmpty()) {
            elements.add(queue.pop());
        }

        Assert.assertEquals(101, elements.size());

        for (int i = 0; i < elements.size() - 2; i++) {
            Assert.assertTrue(elements.get(i) >= elements.get(i + 1));
        }
    }
}
