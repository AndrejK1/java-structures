package learning.tree;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

public class BinaryTree<T extends Comparable<T>> implements Tree<T> {
    private int size;
    private BinaryNode<T> root;

    @Override
    public boolean add(T element) {
        root = innerAdd(root, element);
        size++;
        return true;
    }

    private BinaryNode<T> innerAdd(BinaryNode<T> current, T value) {
        if (current == null) {
            return new BinaryNode<>(value);
        }

        if (current.value.compareTo(value) > 0) {
            current.leftChild = innerAdd(current.leftChild, value);
        } else {
            current.rightChild = innerAdd(current.rightChild, value);
        }

        return current;
    }

    @Override
    public boolean addSubTree(Tree<T> subTree) {
        if (!(subTree instanceof BinaryTree)) {
            throw new IllegalArgumentException("Subtree must be BinaryTree type");
        }

        BinaryNode<T> subTreeRoot = ((BinaryTree<T>) subTree).root;

        root = innerAddSubTree(root, subTreeRoot);
        size += calculateSize(subTreeRoot);
        return true;
    }

    private BinaryNode<T> innerAddSubTree(BinaryNode<T> current, BinaryNode<T> subTree) {
        if (current == null) {
            return subTree;
        }

        if (current.value.compareTo(subTree.getValue()) > 0) {
            current.leftChild = innerAddSubTree(current.leftChild, subTree);
        } else {
            current.rightChild = innerAddSubTree(current.rightChild, subTree);
        }

        return current;
    }
    @Override
    public boolean remove(T element) {
        int previousSize = size;
        root = innerRemove(root, element);
        return previousSize > size;
    }

    private BinaryNode<T> innerRemove(BinaryNode<T> current, T value) {
        if (current == null) {
            return null;
        }

        if (current.value.compareTo(value) == 0) {
            if (current.leftChild == null && current.rightChild == null) {
                size--;
                return null;
            } else if (current.rightChild == null) {
                BinaryNode<T> predecessor = predecessor(current);
                current.value = predecessor.value;
                current.leftChild = innerRemove(current.leftChild, current.value);
            } else {
                BinaryNode<T> successor = successor(current);
                current.value = successor.value;
                current.rightChild = innerRemove(current.rightChild, current.value);
            }
        }

        if (current.value.compareTo(value) > 0) {
            current.leftChild = innerRemove(current.leftChild, value);
        } else {
            current.rightChild = innerRemove(current.rightChild, value);
        }

        return current;
    }

    private BinaryNode<T> successor(BinaryNode<T> node) {
        BinaryNode<T> current = node.rightChild;

        while (current.leftChild != null) {
            current = current.leftChild;
        }

        return current;
    }

    private BinaryNode<T> predecessor(BinaryNode<T> node) {
        BinaryNode<T> current = node.leftChild;

        while (current.rightChild != null) {
            current = current.rightChild;
        }

        return current;
    }

    @Override
    public boolean contains(T element) {
        return innerContains(root, element);
    }

    private boolean innerContains(BinaryNode<T> current, T value) {
        if (current == null) {
            return false;
        }

        if (current.value.compareTo(value) == 0) {
            return true;
        }

        if (current.value.compareTo(value) > 0) {
            return innerContains(current.leftChild, value);
        }

        return innerContains(current.rightChild, value);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public void forEach(TreeTraversal treeTraversalType, Consumer<T> action) {
        switch (treeTraversalType) {
            case INORDER:
                forEachInorder(root, action);
                break;
            case POSTORDER:
                forEachPostorder(root, action);
                break;
            case PREORDER:
                forEachPreorder(root, action);
                break;
            case LEVELS:
                forEachByLayers(action);
                break;
        }
    }

    private void forEachInorder(BinaryNode<T> current, Consumer<T> action) {
        if (current == null) {
            return;
        }

        forEachInorder(current.leftChild, action);
        action.accept(current.value);
        forEachInorder(current.rightChild, action);
    }

    private void forEachPreorder(BinaryNode<T> current, Consumer<T> action) {
        if (current == null) {
            return;
        }

        action.accept(current.value);
        forEachPreorder(current.leftChild, action);
        forEachPreorder(current.rightChild, action);
    }

    private void forEachPostorder(BinaryNode<T> current, Consumer<T> action) {
        if (current == null) {
            return;
        }

        forEachPostorder(current.leftChild, action);
        forEachPostorder(current.rightChild, action);
        action.accept(current.value);
    }

    private void forEachByLayers(Consumer<T> action) {
        if (size == 0) {
            return;
        }

        Queue<BinaryNode<T>> nodes = new LinkedList<>();

        nodes.add(root);

        while (!nodes.isEmpty()) {
            BinaryNode<T> node = nodes.poll();

            action.accept(node.value);

            if (node.leftChild != null) {
                nodes.add(node.leftChild);
            }

            if (node.rightChild != null) {
                nodes.add(node.rightChild);
            }
        }
    }

    private int calculateSize(Node<T> current) {
        if (current == null) {
            return 0;
        }

        return 1 + calculateSize(current.getLeftChild()) + calculateSize(current.getRightChild());
    }

    public int height() {
        if (size <= 1) {
            return size;
        }

        return getMaxLeafHeight(root, 0);
    }

    private int getMaxLeafHeight(BinaryNode<T> node, int currentHeight) {
        if (node == null) {
            return currentHeight;
        }

        return Math.max(
                getMaxLeafHeight(node.leftChild, currentHeight + 1),
                getMaxLeafHeight(node.rightChild, currentHeight + 1)
        );
    }

    @Override
    public String showStructure() {
        if (size == 0) {
            return "Binary Tree: No elements.";
        }

        StringBuilder resultStr = new StringBuilder()
                .append("BinaryTree: size = ").append(size)
                .append(", height: ").append(height())
                .append("\n")
                .append("nodes: ");

        forEachByLayers(i -> resultStr.append(i).append(" "));

        return resultStr.toString();
    }

    @Setter
    @Getter
    @AllArgsConstructor
    private static class BinaryNode<T> implements Node<T> {
        private T value;
        private BinaryNode<T> leftChild;
        private BinaryNode<T> rightChild;

        public BinaryNode(T value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node{" + " value = " + value +
                    ", l: " + (leftChild == null ? "n" : "y") +
                    ", r: " + (rightChild == null ? "n" : "y") +
                    " }";
        }
    }

}
