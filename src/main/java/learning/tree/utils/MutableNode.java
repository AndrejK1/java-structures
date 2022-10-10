package learning.tree.utils;

import learning.tree.Tree;

public interface MutableNode<T> extends Tree.Node<T> {
    void setLeftChild(MutableNode<T> node);
    void setRightChild(MutableNode<T> node);
    void setValue(T value);

    @Override
    MutableNode<T> getLeftChild();

    @Override
    MutableNode<T> getRightChild();

    @Override
    T getValue();
}
