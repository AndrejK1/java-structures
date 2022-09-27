package learning.list;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

@Slf4j
public class LinkedListTest {

    @Test
    public void testLinkedList() {
        MutableList<Integer> linkedList = new LinkedList<>();

        Assert.assertEquals(0, linkedList.size());
        Assert.assertFalse(linkedList.contains(0));

        linkedList.add(1);

        Assert.assertEquals(1, linkedList.size());
        Assert.assertFalse(linkedList.contains(0));
        Assert.assertTrue(linkedList.contains(1));
        Assert.assertEquals(0, linkedList.findPos(1));

        linkedList.remove(0);

        Assert.assertEquals(0, linkedList.size());
        Assert.assertFalse(linkedList.contains(1));
        Assert.assertEquals(-1, linkedList.findPos(1));

        linkedList.add(2);
        linkedList.add(3);
        linkedList.add(10, 1);

        Assert.assertEquals(3, linkedList.size());
        Assert.assertTrue(linkedList.contains(2));
        Assert.assertEquals(0, linkedList.findPos(2));
        Assert.assertEquals(1, linkedList.findPos(10));
        Assert.assertEquals(2, linkedList.findPos(3));

        linkedList.remove(1);

        Assert.assertEquals(2, linkedList.size());
        Assert.assertTrue(linkedList.contains(2));
        Assert.assertFalse(linkedList.contains(10));
        Assert.assertEquals(0, linkedList.findPos(2));
        Assert.assertEquals(1, linkedList.findPos(3));

        Assert.assertTrue(linkedList.remove(new Integer(2)));
        Assert.assertFalse(linkedList.remove(new Integer(2)));

        Assert.assertEquals(1, linkedList.size());
        Assert.assertEquals(0, linkedList.findPos(3));

        linkedList.clear();

        Assert.assertEquals(0, linkedList.size());
        Assert.assertFalse(linkedList.contains(3));
    }
}
