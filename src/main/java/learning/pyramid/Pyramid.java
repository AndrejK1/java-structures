package learning.pyramid;

import learning.queue.Queue;

import java.util.ArrayList;
import java.util.List;

/**
 * created mainly for sorting
 */
public class Pyramid<T extends Comparable<T>> implements Queue<T> {
    private final List<T> elements;
    private int size;

    public Pyramid() {
        this(16);
    }

    public Pyramid(int size) {
        this.elements = new ArrayList<>(size);
    }

    private Pyramid(List<T> elements) {
        this.elements = elements;
        this.size = elements.size();

        for (int i = size / 2 - 1; i >= 0; i--) {
            moveDown(i);
        }
    }

    public static <T extends Comparable<T>> Pyramid<T> fromList(List<T> listToReorder) {
        return new Pyramid<>(listToReorder);
    }

    @Override
    public void push(T element) {
        elements.add(element);
        moveUp(size++);
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Pyramid is empty!");
        }

        if (size == 1) {
            return elements.remove(--size);
        }

        T first = elements.get(0);
        elements.set(0, elements.remove(--size));
        moveDown(0);

        return first;
    }

    public T popWithoutClear() {
        if (isEmpty()) {
            throw new IllegalStateException("Pyramid is empty!");
        }

        if (size == 1) {
            return elements.get(--size);
        }

        T first = elements.get(0);
        elements.set(0, elements.get(--size));
        moveDown(0);

        return first;
    }

    @Override
    public T peek() {
        return elements.get(0);
    }

    @Override
    public void clear() {
        elements.clear();
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public String showStructure() {
        return "Pyramid:\nelements: " + elements;
    }

    private void moveUp(int currentIndex) {
        T newNode = elements.get(currentIndex);
        int parentPosition;

        while (currentIndex > 0) {
            parentPosition = (currentIndex - 1) / 2;

            if (elements.get(parentPosition).compareTo(newNode) >= 0) {
                break;
            }

            elements.set(currentIndex, elements.get(parentPosition));
            currentIndex = parentPosition;
        }

        elements.set(currentIndex, newNode);
    }

    private void moveDown(int currentIndex) {
        int count = size;
        T top = elements.get(currentIndex);

        int leftChildPosition;
        int rightChildPosition;
        int largerChild;

        while (currentIndex < count / 2) {
            leftChildPosition = 2 * currentIndex + 1;
            rightChildPosition = leftChildPosition + 1;

            if (rightChildPosition < count &&
                    elements.get(rightChildPosition).compareTo(elements.get(leftChildPosition)) > 0) {
                largerChild = rightChildPosition;
            } else {
                largerChild = leftChildPosition;
            }

            if (top.compareTo(elements.get(largerChild)) >= 0) {
                break;
            }

            elements.set(currentIndex, elements.get(largerChild));
            currentIndex = largerChild;
        }

        elements.set(currentIndex, top);
    }
}
