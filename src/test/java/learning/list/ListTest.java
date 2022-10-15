package learning.list;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class ListTest {

    @Test
    public void testLinkedList() {
        innerListTest(new SimpleLinkedList<>());
    }

    @Test
    public void testArrayListList() {
        innerListTest(new SimpleArrayList<>());
    }

    private static void innerListTest(MutableList<Integer> list) {
        Assert.assertEquals(0, list.size());
        Assert.assertFalse(list.contains(0));

        list.add(1);

        Assert.assertEquals(1, list.size());
        Assert.assertFalse(list.contains(0));
        Assert.assertTrue(list.contains(1));
        Assert.assertEquals(0, list.findPos(1));

        list.remove(0);

        Assert.assertEquals(0, list.size());
        Assert.assertFalse(list.contains(1));
        Assert.assertEquals(-1, list.findPos(1));

        list.add(2);
        list.add(3);
        list.add(10, 1);

        Assert.assertEquals(3, list.size());
        Assert.assertTrue(list.contains(2));
        Assert.assertEquals(0, list.findPos(2));
        Assert.assertEquals(1, list.findPos(10));
        Assert.assertEquals(2, list.findPos(3));

        list.remove(1);

        Assert.assertEquals(2, list.size());
        Assert.assertTrue(list.contains(2));
        Assert.assertFalse(list.contains(10));
        Assert.assertEquals(0, list.findPos(2));
        Assert.assertEquals(1, list.findPos(3));

        Assert.assertTrue(list.remove(new Integer(2)));
        Assert.assertFalse(list.remove(new Integer(2)));

        Assert.assertEquals(1, list.size());
        Assert.assertEquals(0, list.findPos(3));

        list.clear();

        Assert.assertEquals(0, list.size());
        Assert.assertFalse(list.contains(3));

        java.util.List<Integer> largeList = IntStream.rangeClosed(1, 1000).boxed().collect(Collectors.toList());
        largeList.forEach(list::add);
        Assert.assertEquals(largeList.size(), list.size());
        largeList.forEach(v -> Assert.assertTrue(list.contains(v)));

        list.set(34, 3);

        Assert.assertEquals(largeList.size(), list.size());
        Assert.assertEquals(34, list.get(3).intValue());
        Assert.assertTrue(list.contains(34));
    }
}
