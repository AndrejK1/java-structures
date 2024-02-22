package learning.pyramid;

import learning.list.MutableList;
import learning.list.SimpleArrayList;
import learning.queue.PriorityQueue;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class SimplePriorityPyramid<K extends Comparable<K>, T> implements PriorityQueue<K, T> {
    private final boolean highPriorityBased;
    private final K defaultPriority;
    private final MutableList<Node<K, T>> elements;

    public SimplePriorityPyramid(int size, K defaultPriority, boolean highPriorityBased) {
        this.elements = new SimpleArrayList<>(size);
        this.defaultPriority = defaultPriority;
        this.highPriorityBased = highPriorityBased;
    }

    @Override
    public void push(T element) {
        this.push(element, defaultPriority);
    }

    @Override
    public void push(T element, K priority) {
        elements.add(new Node<>(priority, element));
        moveUp(elements.size() - 1);
    }

    @Override
    public boolean remove(T element) {
        int elementIndex = getElementIndex(element);

        if (elementIndex == -1) {
            return false;
        }

        elements.remove(elementIndex);
        return true;
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Pyramid is empty!");
        }

        if (elements.size() == 1) {
            return elements.remove(0).data;
        }

        Node<K, T> first = elements.get(0);
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
        Node<K, T> newNode = elements.get(currentIndex);
        int parentPosition;

        while (currentIndex > 0) {
            parentPosition = getParentPosition(currentIndex);

            int parentToNewCompareResult = elements.get(parentPosition).priority.compareTo(newNode.priority);

            if (highPriorityBased == (parentToNewCompareResult > 0)) {
                break;
            }

            elements.set(elements.get(parentPosition), currentIndex);
            currentIndex = parentPosition;
        }

        elements.set(newNode, currentIndex);
    }

    private void moveDown(int currentIndex) {
        int count = elements.size();
        Node<K, T> top = elements.get(currentIndex);

        int leftChildPosition;
        int rightChildPosition;
        int largerChild;

        while (currentIndex < count / 2) {
            leftChildPosition = getLeftChildPosition(currentIndex);
            rightChildPosition = getRightChildPosition(currentIndex);

            if (rightChildPosition < count &&
                    highPriorityBased == (elements.get(rightChildPosition).priority.compareTo(elements.get(leftChildPosition).priority) > 0)) {
                largerChild = rightChildPosition;
            } else {
                largerChild = leftChildPosition;
            }

            if (highPriorityBased == (elements.get(largerChild).priority.compareTo(top.priority) < 0)) {
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

    private int getElementIndex(T element) {
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getData().equals(element)) {
                return i;
            }
        }

        return -1;
    }

    @Getter
    @AllArgsConstructor
    private static class Node<K extends Comparable<K>, T> {
        private K priority;
        private T data;

        @Override
        public String toString() {
            return priority + ": " + data;
        }
    }
}

