package learning.tree.binary;

import learning.tree.Tree;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.function.BiFunction;
import java.util.function.Consumer;

//@Slf4j
@RequiredArgsConstructor
public class BinaryTree<T> implements Tree<T> {
    private final BiFunction<T, T, Integer> comparator;
    private Node<T> root;

    @Override
    public void add(T value) {
        root = _innerInsert(root, value);
    }

    @Override
    public void remove(T value) {
        root = _innerRemove(root, value);
    }

    @Override
    public void forEach(Consumer<T> action) {
        forEachDFSInorder(action);
    }

    public void forEachDFSInorder(Consumer<T> action) {
        _nodeActionDFSInorder(root, action);
    }

    public void forEachBFS(Consumer<T> action) {
        if (root == null) {
            System.out.println("Tree is empty!");
        }

        Queue<Node<T>> nodes = new LinkedList<>();
        nodes.offer(root);

        while (!nodes.isEmpty()) {
            Node<T> node = nodes.poll();

            action.accept(node.value);

            if (node.left != null) {
                nodes.offer(node.left);
            }

            if (node.right != null) {
                nodes.offer(node.right);
            }
        }
    }

    public boolean contains(T value) {
        return _innerContains(root, value);
    }

    @Override
    public void print() {
        System.out.println(_innerPrint(root));
    }

    private Node<T> _innerRemove(Node<T> node, T value) {
        if (node == null) {
            return null;
        }

        if (comparator.apply(node.value, value) == 0) {
            if (node.left == null && node.right == null) {
                node = null;
            } else if (node.right == null) {
                node.value = predecessor(node);
                node.left = _innerRemove(node.left, node.value);
            } else {
                node.value = successor(node);
                node.right = _innerRemove(node.right, node.value);
            }
        } else if (comparator.apply(value, node.value) > 0) {
            node.right = _innerRemove(node.right, value);
        } else {
            node.left = _innerRemove(node.left, value);
        }

        return node;
    }

    private T successor(Node<T> root) {
        root = root.right;

        while (root.left != null) {
            root = root.left;
        }

        return root.value;
    }

    private T predecessor(Node<T> root) {
        root = root.left;

        while (root.right != null) {
            root = root.right;
        }

        return root.value;
    }

    private void _nodeActionDFSInorder(Node<T> node, Consumer<T> action) {
        if (node == null) {
            return;
        }

        _nodeActionDFSInorder(node.left, action);
        action.accept(node.value);
        _nodeActionDFSInorder(node.right, action);
    }

    private Node<T> _innerInsert(Node<T> pos, T value) {
        if (pos == null) {
            return new Node<>(value);
        } else if (comparator.apply(value, pos.getValue()) > 0) {
            pos.right = _innerInsert(pos.getRight(), value);
        } else if (comparator.apply(value, pos.getValue()) < 0) {
            pos.left = _innerInsert(pos.getLeft(), value);
        } else {
            System.out.println("Same value - " + value);
        }

        return pos;
    }

    private boolean _innerContains(Node<T> node, T value) {
        if (node == null) {
            return false;
        }

        if (comparator.apply(node.value, value) == 0) {
            return true;
        }

        if (comparator.apply(value, node.value) > 0) {
            return _innerContains(node.right, value);
        } else {
            return _innerContains(node.left, value);
        }
    }

    private String _innerPrint(Node<T> node) {
        if (node == null) {
            return null;
        }

        return node.value +
                Optional.ofNullable(_innerPrint(node.left)).map(s -> "\n\t" + s.replace("\n", "\n\t")).orElse("") +
                Optional.ofNullable(_innerPrint(node.right)).map(s -> "\n\t" + s.replace("\n", "\n\t")).orElse("");
    }

    @Data
    @AllArgsConstructor
    private static class Node<V> {
        private V value;
        private Node<V> left;
        private Node<V> right;

        private Node(V value) {
            this.value = value;
        }
    }


}
