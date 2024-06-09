package learning.tree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class TreeTest {

    @Test
    void testBST() {
        List<Integer> values = Arrays.asList(10, 5, 2, 7, 15, 13, 13, 18, 21, 17, 10);

        //          10
        //    5           15
        //  2   7     13       18
        //          10   13  17   21

        Tree<Integer> tree = new BinaryTree<>();
        values.forEach(tree::add);

        Assertions.assertEquals(values.size(), tree.size());
        Assertions.assertEquals(4, tree.height());
        values.forEach(i -> Assertions.assertTrue(tree.contains(i)));

        List<Integer> firstOrderTest = new ArrayList<>();
        tree.forEach(TreeTraversal.INORDER, firstOrderTest::add);

        for (int i = 1; i < firstOrderTest.size(); i++) {
            Assertions.assertTrue(firstOrderTest.get(i) >= firstOrderTest.get(i - 1));
        }

        Assertions.assertFalse(tree.remove(100));
        Assertions.assertEquals(values.size(), tree.size());

        Assertions.assertTrue(tree.remove(15));
        Assertions.assertEquals(values.size() - 1, tree.size());
        Assertions.assertEquals(4, tree.height());
        Assertions.assertFalse(tree.contains(15));

        List<Integer> secondOrderTest = new ArrayList<>();
        tree.forEach(TreeTraversal.INORDER, secondOrderTest::add);

        for (int i = 1; i < secondOrderTest.size(); i++) {
            Assertions.assertTrue(secondOrderTest.get(i) >= secondOrderTest.get(i - 1));
        }

        Assertions.assertTrue(tree.remove(10));
        Assertions.assertTrue(tree.remove(13));
        Assertions.assertTrue(tree.remove(21));

        List<Integer> thirdOrderTest = new ArrayList<>();
        tree.forEach(TreeTraversal.INORDER, thirdOrderTest::add);

        for (int i = 1; i < thirdOrderTest.size(); i++) {
            Assertions.assertTrue(thirdOrderTest.get(i) >= thirdOrderTest.get(i - 1));
        }

        Assertions.assertEquals(values.size() - 4, tree.size());
        Assertions.assertEquals(3, tree.height());

        tree.clear();

        Assertions.assertEquals(0, tree.size());
        Assertions.assertEquals(0, tree.height());
        Assertions.assertFalse(tree.contains(10));
        Assertions.assertFalse(tree.remove(10));
    }
}
