package learning.tree.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class MutableNodeImpl<T> implements MutableNode<T> {
    private T value;
    private MutableNode<T> leftChild;
    private MutableNode<T> rightChild;

    @Override
    public String toString() {
        return "Node{" + " value = " + value +
                ", l: " + (leftChild == null ? "n" : "y") +
                ", r: " + (rightChild == null ? "n" : "y") +
                " }";
    }
}
