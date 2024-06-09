package learning.pyramid;

import learning.queue.PriorityQueue;
import learning.queue.Queue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

class PyramidTest {
    private final Random r = new Random();

    @Test
    void testIntegerPriorityPyramid() {
        int defaultPriority = 100;

        PriorityQueue<Integer, Integer> queue = new IntegerPriorityPyramid<>(100, defaultPriority, true);

        Assertions.assertTrue(queue.isEmpty());

        Stream.generate(() -> r.nextInt(defaultPriority * 2))
                .limit(100)
                .forEach(i -> queue.push(i, i));

        queue.push(defaultPriority);

        List<Integer> elements = new ArrayList<>();

        while (!queue.isEmpty()) {
            elements.add(queue.pop());
        }

        Assertions.assertEquals(101, elements.size());

        for (int i = 0; i < elements.size() - 2; i++) {
            Assertions.assertTrue(elements.get(i) >= elements.get(i + 1));
        }
    }

    @Test
    void testInverseDoublePriorityPyramid() {
        int defaultPriority = 100;

        PriorityQueue<Double, Integer> queue = new DoublePriorityPyramid<>(100, defaultPriority, false);

        Assertions.assertTrue(queue.isEmpty());

        Stream.generate(() -> r.nextDouble(defaultPriority * 2))
                .limit(100)
                .forEach(i -> queue.push(i.intValue(), i));

        queue.push(defaultPriority);

        List<Integer> elements = new ArrayList<>();

        while (!queue.isEmpty()) {
            elements.add(queue.pop());
        }

        Assertions.assertEquals(101, elements.size());

        for (int i = 0; i < elements.size() - 2; i++) {
            Assertions.assertTrue(elements.get(i) <= elements.get(i + 1));
        }
    }

    @Test
    void testPyramid() {
        int defaultPriority = 100;

        Queue<Integer> queue = new Pyramid<>(100);

        Assertions.assertTrue(queue.isEmpty());

        Stream.generate(() -> r.nextInt(defaultPriority * 2))
                .limit(100)
                .forEach(queue::push);

        queue.push(defaultPriority);

        List<Integer> elements = new ArrayList<>();

        while (!queue.isEmpty()) {
            elements.add(queue.pop());
        }

        Assertions.assertEquals(101, elements.size());

        for (int i = 0; i < elements.size() - 2; i++) {
            Assertions.assertTrue(elements.get(i) >= elements.get(i + 1));
        }
    }
}
