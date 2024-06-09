package learning.tree.old;


import learning.tree.old.binary.BinaryTree;
import learning.tree.old.binary.IntegerTree;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class OldTreeTest {

    @Test
    void testBST() {
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
