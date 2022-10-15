package learning.pyramid;

import learning.list.MutableList;
import learning.list.SimpleArrayList;
import learning.queue.PriorityQueue;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class IntegerPriorityPyramid<T> implements PriorityQueue<T> {
    private final int defaultPriority;
    private final MutableList<Node<T>> elements;

    public IntegerPriorityPyramid() {
        this(16, Integer.MAX_VALUE / 2);
    }

    public IntegerPriorityPyramid(int size, int defaultPriority) {
        this.elements = new SimpleArrayList<>(size);
        this.defaultPriority = defaultPriority;
    }

    @Override
    public void push(T element) {
        this.push(element, defaultPriority);
    }

    @Override
    public void push(T element, int priority) {
        elements.add(new Node<>(priority, element));
        moveUp(elements.size() - 1);
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Pyramid is empty!");
        }

        if (elements.size() == 1) {
            return elements.remove(0).data;
        }

        Node<T> first = elements.get(0);
        elements.set(elements.remove(elements.size() - 1), 0);
        moveDown(0);

        return first.data;
    }

    @Override
    public T peek() {
        return elements.get(0).getData();
    }

    @Override
    public void clear() {
        elements.clear();
    }

    @Override
    public boolean isEmpty() {
        return elements.size() == 0;
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public String showStructure() {
        return "Pyramid:\nelements: " + elements.showStructure();
    }

    private void moveUp(int currentIndex) {
        Node<T> newNode = elements.get(currentIndex);
        int parentPosition;

        while (currentIndex > 0) {
            parentPosition = getParentPosition(currentIndex);

            if (elements.get(parentPosition).priority > newNode.priority) {
                break;
            }

            elements.set(elements.get(parentPosition), currentIndex);
            currentIndex = parentPosition;
        }

        elements.set(newNode, currentIndex);
    }

    private void moveDown(int currentIndex) {
        int count = elements.size();
        Node<T> top = elements.get(currentIndex);

        int leftChildPosition;
        int rightChildPosition;
        int largerChild;

        while (currentIndex < count / 2) {
            leftChildPosition = getLeftChildPosition(currentIndex);
            rightChildPosition = getRightChildPosition(currentIndex);

            if (rightChildPosition < count &&
                    elements.get(rightChildPosition).priority > elements.get(leftChildPosition).priority) {
                largerChild = rightChildPosition;
            } else {
                largerChild = leftChildPosition;
            }

            if (elements.get(largerChild).priority <= top.priority) {
                break;
            }

            elements.set(elements.get(largerChild), currentIndex);
            currentIndex = largerChild;
        }

        elements.set(top, currentIndex);
    }

    private int getLeftChildPosition(int pos) {
        return 2 * pos + 1;
    }

    private int getRightChildPosition(int pos) {
        return 2 * pos + 2;
    }

    private int getParentPosition(int pos) {
        return (pos - 1) / 2;
    }

    @Getter
    @AllArgsConstructor
    private static class Node<T> {
        private int priority;
        private T data;

        @Override
        public String toString() {
            return priority + ": " + data;
        }
    }
}

