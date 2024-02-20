package learning.examples.db;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ImprovedBagTest {

    @Test
    public void testFindSolutionCount() {
        long solutionCount = ImprovedBag.findSolutionCount(5, List.of(5, 1, 4));
        Assert.assertEquals(4, solutionCount);
    }

    @Test
    public void testFindMinimalThingsCount() {
        long solutionCount = ImprovedBag.findMinimalThingsCount(13, List.of(5, 1, 4));
        Assert.assertEquals(3, solutionCount);
    }

    @Test
    public void testFindMinimalThingsCountWithSolution() {
        ImprovedBag.BagInfo solution = ImprovedBag.findMinimalThingsCountWithSolution(13, List.of(5, 1, 4));
        Assert.assertEquals(3, solution.getThingsCount());
        Assert.assertEquals(13, solution.getThingsSum());

        Map<Integer, Long> frequencies = solution.getThings()
                .stream()
                .collect(Collectors.groupingBy(item -> item, Collectors.counting()));

        Assert.assertEquals(2L, (long) frequencies.remove(4));
        Assert.assertEquals(1L, (long) frequencies.remove(5));
        Assert.assertTrue(frequencies.isEmpty());
    }
}