package learning.examples.db;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class ImprovedBagTest {

    @Test
    void testFindSolutionCount() {
        long solutionCount = ImprovedBag.findSolutionCount(5, List.of(5, 1, 4));
        Assertions.assertEquals(4, solutionCount);
    }

    @Test
    void testFindMinimalThingsCount() {
        long solutionCount = ImprovedBag.findMinimalThingsCount(13, List.of(5, 1, 4));
        Assertions.assertEquals(3, solutionCount);
    }

    @Test
    void testFindMinimalThingsCountWithSolution() {
        ImprovedBag.BagInfo solution = ImprovedBag.findMinimalThingsCountWithSolution(13, List.of(5, 1, 4));
        Assertions.assertEquals(3, solution.getThingsCount());
        Assertions.assertEquals(13, solution.getThingsSum());

        Map<Integer, Long> frequencies = solution.getThings()
                .stream()
                .collect(Collectors.groupingBy(item -> item, Collectors.counting()));

        Assertions.assertEquals(2L, (long) frequencies.remove(4));
        Assertions.assertEquals(1L, (long) frequencies.remove(5));
        Assertions.assertTrue(frequencies.isEmpty());
    }
}