package learning.list;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
class ListTest {

    @Test
    void testLinkedList() {
        innerListTest(new SimpleLinkedList<>());
    }

    @Test
    void testArrayListList() {
        innerListTest(new SimpleArrayList<>());
    }

    private void innerListTest(MutableList<Integer> list) {
        Assertions.assertEquals(0, list.size());
        Assertions.assertFalse(list.contains(0));

        list.add(1);

        Assertions.assertEquals(1, list.size());
        Assertions.assertFalse(list.contains(0));
        Assertions.assertTrue(list.contains(1));
        Assertions.assertEquals(0, list.findPos(1));

        list.remove(0);

        Assertions.assertEquals(0, list.size());
        Assertions.assertFalse(list.contains(1));
        Assertions.assertEquals(-1, list.findPos(1));

        list.add(2);
        list.add(3);
        list.add(10, 1);

        Assertions.assertEquals(3, list.size());
        Assertions.assertTrue(list.contains(2));
        Assertions.assertEquals(0, list.findPos(2));
        Assertions.assertEquals(1, list.findPos(10));
        Assertions.assertEquals(2, list.findPos(3));

        list.remove(1);

        Assertions.assertEquals(2, list.size());
        Assertions.assertTrue(list.contains(2));
        Assertions.assertFalse(list.contains(10));
        Assertions.assertEquals(0, list.findPos(2));
        Assertions.assertEquals(1, list.findPos(3));

        Assertions.assertTrue(list.remove(new Integer(2)));
        Assertions.assertFalse(list.remove(new Integer(2)));

        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals(0, list.findPos(3));

        list.clear();

        Assertions.assertEquals(0, list.size());
        Assertions.assertFalse(list.contains(3));

        java.util.List<Integer> largeList = IntStream.rangeClosed(1, 1000).boxed().collect(Collectors.toList());
        largeList.forEach(list::add);
        Assertions.assertEquals(largeList.size(), list.size());
        largeList.forEach(v -> Assertions.assertTrue(list.contains(v)));

        list.set(34, 3);

        Assertions.assertEquals(largeList.size(), list.size());
        Assertions.assertEquals(34, list.get(3).intValue());
        Assertions.assertTrue(list.contains(34));
    }
}
