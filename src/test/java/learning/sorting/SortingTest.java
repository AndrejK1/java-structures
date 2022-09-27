package learning.sorting;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class SortingTest {

    private final Random r = new Random();

    @Test
    public void runAllSortingEfficiencyTest() {
        List<ListSorter> enabledSorters = Arrays.asList(
                ListSorters.BUBBLE_SORTER
                , ListSorters.SELECTION_SORTER
                , ListSorters.INSERTION_SORTER
                , ListSorters.SHELL_SORTER
                , ListSorters.MERGE_SORTER
                , ListSorters.QUICK_SORTER
        );

        testSortingAlgorithms(enabledSorters, 50_000 + r.nextInt(10_000));
    }

    private void testSortingAlgorithms(Collection<ListSorter> sorters, int elements) {
        log.info("Sorting test on shuffled list of " + elements + " Integer objects");

        int duplicatesCount = elements / 5 + r.nextInt(elements / 4);

        List<Integer> list = IntStream.range(0, elements - duplicatesCount)
                .boxed()
                .collect(Collectors.toList());

        IntStream.range(0, duplicatesCount).forEach(list::add);

        Collections.shuffle(list);

        sorters.forEach(sorter -> runSortingEfficiencyTest(sorter, list));

        log.info("Sorting test on already sorted list of " + elements + " Integer objects");
        Collections.sort(list);

        sorters.forEach(sorter -> runSortingEfficiencyTest(sorter, list));

        log.info("Sorting test on reverse sorted list of " + elements + " Integer objects");
        Collections.reverse(list);
        sorters.forEach(sorter -> runSortingEfficiencyTest(sorter, list));

        log.info("Sorting test on twisted pairs list of " + elements + " Integer objects");
        Collections.sort(list);
        for (int i = 0; i < list.size() - 1; i += 2) {
            Integer temp = list.get(i);
            list.set(i, list.get(i + 1));
            list.set(i + 1, temp);
        }

        sorters.forEach(sorter -> runSortingEfficiencyTest(sorter, list));
    }

    public <T extends Comparable<T>> void runSortingEfficiencyTest(ListSorter sorter, List<T> list) {
        ArrayList<T> listForSort = new ArrayList<>(list);

        long start = System.currentTimeMillis();
        sorter.sort(listForSort);
        long end = System.currentTimeMillis();

        for (int i = 1; i < listForSort.size() - 1; i++) {
            Assert.assertFalse(listForSort.get(i).compareTo(listForSort.get(i - 1)) < 0);
        }

        log.info(String.format("%-20s: Sorting done in %s ms\t(validated)",
                sorter.getClass().getSimpleName(), end - start));
    }
}
