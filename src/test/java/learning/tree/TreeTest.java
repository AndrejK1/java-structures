package learning.tree;


import learning.tree.binary.BinaryTree;
import learning.tree.binary.IntegerTree;
import learning.tree.rb.AVLTree;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class TreeTest {

    @Test
    public void testAVLT() {
        List<Integer> values = Arrays.asList(10, 5, 2, 7, 15, 13, 18, 21, 17, 30);

        Tree<Integer> tree = new AVLTree<>(Integer::compareTo);
        values.forEach(tree::add);

        tree.print();

        tree.forEach(v -> System.out.print(v + " "));
    }

    @Test
    public void testBST() {
        List<Integer> values = Arrays.asList(10, 5, 2, 7, 15, 13, 18, 21, 17);

        Tree<Integer> tree = new IntegerTree();
        values.forEach(tree::add);

        tree.remove(10);
        tree.print();

        System.out.print("\n DFS Inorder - \t");
        tree.forEach(v -> System.out.print(v + " "));

        System.out.print("\n BFS - \t\t\t");
        ((BinaryTree<Integer>) tree).forEachBFS(v -> System.out.print(v + " "));
        System.out.println("\n");
    }
}
