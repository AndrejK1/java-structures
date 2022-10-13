package learning.list;

import additional.StringStructureSupport;
import learning.queue.Queue;
import lombok.AllArgsConstructor;

public class SimpleLinkedList<T> implements MutableList<T>, Queue<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T element) {
        if (size == 0) {
            insertFirst(element);
        } else {
            insertLast(element);
        }

    }

    @Override
    public void add(T element, int position) {
        if (position == 0) {
            insertFirst(element);
        } else if (position == size) {
            insertLast(element);
        } else {
            insertMiddle(element, position);
        }
    }

    @Override
    public T get(int position) {
        checkSize(position);

        Node<T> result = first;

        for (int i = 0; i < position; i++) {
            result = result.next;
        }

        return result.value;
    }

    @Override
    public T remove(int position) {
        checkSize(position);

        if (position == 0) {
            return removeFirst().value;
        }

        if (position == size - 1) {
            return removeLast().value;
        }

        Node<T> previousAtPosition = removeAtMiddlePosition(position);

        return previousAtPosition.value;
    }

    @Override
    public boolean remove(T element) {
        Node<T> current = first;

        for (int i = 0; i < size; i++) {
            T currentValue = current.value;

            if ((element == null && currentValue == null) || (currentValue != null && currentValue.equals(element))) {
                remove(i);
                return true;
            }

            current = current.next;
        }

        return false;
    }

    @Override
    public boolean contains(T element) {
        return findPos(element) != -1;
    }

    @Override
    public int findPos(T element) {
        int pos = -1;

        Node<T> current = first;

        for (int i = 0; i < size; i++) {
            if (current.value != null && current.value.equals(element)) {
                pos = i;
                break;
            }

            current = current.next;
        }

        return pos;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void push(T element) {
        add(element);
    }

    @Override
    public T pop() {
        checkSize(0);
        return removeFirst().value;
    }

    @Override
    public T peek() {
        checkSize(0);
        return get(0);
    }

    @Override
    public void clear() {
        first = null;
        last = null;
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
        Node<T> current = this.first;

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < size; i++) {
            result.append(current.value).append(" ");
            current = current.next;
        }

        return "LinkedList:\n" +
                "first: " + (first == null ? null : first.showStructure()) + "\n" +
                "last: " + (last == null ? null : last.showStructure()) + "\n" +
                "elements: " + result;
    }

    private void insertFirst(T element) {
        Node<T> previousFirst = first;
        Node<T> newFirst = new Node<>(null, previousFirst, element);

        if (previousFirst != null) {
            previousFirst.previous = newFirst;
        } else {
            last = newFirst;
        }

        first = newFirst;
        size++;
    }

    private void insertLast(T element) {
        Node<T> newNode = new Node<>(last, null, element);
        last.next = newNode;
        last = newNode;
        size++;
    }

    private void insertMiddle(T element, int position) {
        checkSize(position);

        Node<T> currentAtPosition = first;

        for (int i = 0; i < position; i++) {
            currentAtPosition = currentAtPosition.next;
        }

        Node<T> newAtPosition = new Node<>(currentAtPosition.previous, currentAtPosition, element);
        currentAtPosition.previous.next = newAtPosition;
        currentAtPosition.previous = newAtPosition;

        size++;
    }

    private Node<T> removeFirst() {
        Node<T> previousFirst = first;
        Node<T> newFirst = previousFirst.next;

        if (newFirst != null) {
            newFirst.previous = null;
        }

        first = newFirst;

        size--;

        if (size == 0) {
            last = null;
        }

        return previousFirst;
    }

    private Node<T> removeLast() {
        Node<T> previousLast = last;
        Node<T> newLast = previousLast.previous;

        if (newLast != null) {
            newLast.next = null;
        }

        if (newLast == null) {
            first = null;
        }

        size--;

        last = newLast;
        return previousLast;
    }

    private Node<T> removeAtMiddlePosition(int position) {
        Node<T> currentAtPosition = first;

        for (int i = 0; i < position; i++) {
            currentAtPosition = currentAtPosition.next;
        }

        currentAtPosition.previous.next = currentAtPosition.next;
        currentAtPosition.next.previous = currentAtPosition.previous;
        size--;

        return currentAtPosition;
    }

    private void checkSize(int position) {
        if (position < 0 || position >= size) {
            throw new IllegalArgumentException("position " + position + " is not valid");
        }
    }

    @AllArgsConstructor
    private static class Node<T> implements StringStructureSupport {
        private Node<T> previous;
        private Node<T> next;
        private T value;

        @Override
        public String showStructure() {
            return this + ": value = " + value + ", previous = " + previous + ", next = " + next;
        }
    }
}
