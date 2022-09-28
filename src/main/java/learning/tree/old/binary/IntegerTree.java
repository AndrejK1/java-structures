package learning.tree.old.binary;

public class IntegerTree extends BinaryTree<Integer> {

    public IntegerTree() {
        super(Integer::compareTo);
    }
}
