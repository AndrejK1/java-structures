package learning.tree;

import additional.ADT;

import java.util.function.Consumer;

public interface Tree<T extends Comparable<T>> extends ADT {
    boolean add(T element);

    boolean addSubTree(Tree<T> element);

    boolean remove(T element);

    boolean contains(T element);

    int size();
    int height();

    void clear();

    default void forEach(Consumer<T> action) {
        forEach(TreeTraversal.INORDER, action);
    }

    void forEach(TreeTraversal treeTraversalType, Consumer<T> action);

    interface Node<T> {
        Node<T> getLeftChild();
        Node<T> getRightChild();
        T getValue();
    }
}
