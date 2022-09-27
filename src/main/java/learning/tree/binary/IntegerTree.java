package learning.tree.binary;

public class IntegerTree extends BinaryTree<Integer> {

    public IntegerTree() {
        super(Integer::compareTo);
    }
}
