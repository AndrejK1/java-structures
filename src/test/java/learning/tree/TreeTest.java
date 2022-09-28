package learning.tree;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TreeTest {

    @Test
    public void testBST() {
        List<Integer> values = Arrays.asList(10, 5, 2, 7, 15, 13, 13, 18, 21, 17, 10);

        //          10
        //    5           15
        //  2   7     13       18
        //          10   13  17   21

        Tree<Integer> tree = new BinaryTree<>();
        values.forEach(tree::add);

        Assert.assertEquals(values.size(), tree.size());
        Assert.assertEquals(4, tree.height());
        values.forEach(i -> Assert.assertTrue(tree.contains(i)));

        List<Integer> firstOrderTest = new ArrayList<>();
        tree.forEach(TreeTraversal.INORDER, firstOrderTest::add);

        for (int i = 1; i < firstOrderTest.size(); i++) {
            Assert.assertTrue(firstOrderTest.get(i) >= firstOrderTest.get(i - 1));
        }

        Assert.assertFalse(tree.remove(100));
        Assert.assertEquals(values.size(), tree.size());

        Assert.assertTrue(tree.remove(15));
        Assert.assertEquals(values.size() - 1, tree.size());
        Assert.assertEquals(4, tree.height());
        Assert.assertFalse(tree.contains(15));

        List<Integer> secondOrderTest = new ArrayList<>();
        tree.forEach(TreeTraversal.INORDER, secondOrderTest::add);

        for (int i = 1; i < secondOrderTest.size(); i++) {
            Assert.assertTrue(secondOrderTest.get(i) >= secondOrderTest.get(i - 1));
        }

        Assert.assertTrue(tree.remove(10));
        Assert.assertTrue(tree.remove(13));
        Assert.assertTrue(tree.remove(21));

        List<Integer> thirdOrderTest = new ArrayList<>();
        tree.forEach(TreeTraversal.INORDER, thirdOrderTest::add);

        for (int i = 1; i < thirdOrderTest.size(); i++) {
            Assert.assertTrue(thirdOrderTest.get(i) >= thirdOrderTest.get(i - 1));
        }

        Assert.assertEquals(values.size() - 4, tree.size());
        Assert.assertEquals(3, tree.height());

        tree.clear();

        Assert.assertEquals(0, tree.size());
        Assert.assertEquals(0, tree.height());
        Assert.assertFalse(tree.contains(10));
        Assert.assertFalse(tree.remove(10));
    }
}
