package learning.tree;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

public class BinaryTree<T extends Comparable<T>> implements Tree<T> {
    private int size;
    private Node<T> root;

    @Override
    public boolean add(T element) {
        root = innerAdd(root, element);
        size++;
        return true;
    }

    private Node<T> innerAdd(Node<T> current, T value) {
        if (current == null) {
            return new Node<>(value);
        }

        if (current.data.compareTo(value) > 0) {
            current.leftChild = innerAdd(current.leftChild, value);
        } else {
            current.rightChild = innerAdd(current.rightChild, value);
        }

        return current;
    }

    @Override
    public boolean remove(T element) {
        int previousSize = size;
        root = innerRemove(root, element);
        return previousSize > size;
    }

    private Node<T> innerRemove(Node<T> current, T value) {
        if (current == null) {
            return null;
        }

        if (current.data.compareTo(value) == 0) {
            if (current.leftChild == null && current.rightChild == null) {
                size--;
                return null;
            } else if (current.rightChild == null) {
                Node<T> predecessor = predecessor(current);
                current.data = predecessor.data;
                current.leftChild = innerRemove(current.leftChild, current.data);
            } else {
                Node<T> successor = successor(current);
                current.data = successor.data;
                current.rightChild = innerRemove(current.rightChild, current.data);
            }
        }

        if (current.data.compareTo(value) > 0) {
            current.leftChild = innerRemove(current.leftChild, value);
        } else {
            current.rightChild = innerRemove(current.rightChild, value);
        }

        return current;
    }

    private Node<T> successor(Node<T> node) {
        Node<T> current = node.rightChild;

        while (current.leftChild != null) {
            current = current.leftChild;
        }

        return current;
    }

    private Node<T> predecessor(Node<T> node) {
        Node<T> current = node.leftChild;

        while (current.rightChild != null) {
            current = current.rightChild;
        }

        return current;
    }

    @Override
    public boolean contains(T element) {
        return innerContains(root, element);
    }

    private boolean innerContains(Node<T> current, T value) {
        if (current == null) {
            return false;
        }

        if (current.data.compareTo(value) == 0) {
            return true;
        }

        if (current.data.compareTo(value) > 0) {
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

    private void forEachInorder(Node<T> current, Consumer<T> action) {
        if (current == null) {
            return;
        }

        forEachInorder(current.leftChild, action);
        action.accept(current.data);
        forEachInorder(current.rightChild, action);
    }

    private void forEachPreorder(Node<T> current, Consumer<T> action) {
        if (current == null) {
            return;
        }

        action.accept(current.data);
        forEachPreorder(current.leftChild, action);
        forEachPreorder(current.rightChild, action);
    }

    private void forEachPostorder(Node<T> current, Consumer<T> action) {
        if (current == null) {
            return;
        }

        forEachPostorder(current.leftChild, action);
        forEachPostorder(current.rightChild, action);
        action.accept(current.data);
    }

    private void forEachByLayers(Consumer<T> action) {
        if (size == 0) {
            return;
        }

        Queue<Node<T>> nodes = new LinkedList<>();

        nodes.add(root);

        while (!nodes.isEmpty()) {
            Node<T> node = nodes.poll();

            action.accept(node.data);

            if (node.leftChild != null) {
                nodes.add(node.leftChild);
            }

            if (node.rightChild != null) {
                nodes.add(node.rightChild);
            }
        }
    }

    public int height() {
        if (size <= 1) {
            return size;
        }

        return getMaxLeafHeight(root, 0);
    }

    private int getMaxLeafHeight(Node<T> node, int currentHeight) {
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
    private static class Node<T> {
        private T data;
        private Node<T> leftChild;
        private Node<T> rightChild;

        public Node(T data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "Node{" + " data= " + data +
                    ", l: " + (leftChild == null ? "n" : "y") +
                    ", r: " + (rightChild == null ? "n" : "y") +
                    " }";
        }
    }

}
