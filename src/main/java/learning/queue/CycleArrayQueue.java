package learning.queue;

import java.lang.reflect.Array;
import java.util.Arrays;

public class CycleArrayQueue<T> implements Queue<T> {
    private final T[] data;
    private final int maxSize;
    private int queueEndIndex = -1;
    private int queueStartIndex = 0;
    private int elementCount = 0;

    public CycleArrayQueue(int queueSize, Class<T> clazz) {
        maxSize = queueSize;
        data = (T[]) Array.newInstance(clazz, queueSize);
    }

    @Override
    public void push(T element) {
        if (isFull()) {
            throw new IllegalStateException("Queue is full!");
        }

        queueEndIndex = cycleIncrement(queueEndIndex);
        data[queueEndIndex] = element;
        elementCount++;
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty!");
        }

        T element = data[queueStartIndex];
        data[queueStartIndex] = null;
        queueStartIndex = cycleIncrement(queueStartIndex);
        elementCount--;
        return element;
    }

    private int cycleIncrement(int oldPosition) {
        return ++oldPosition % maxSize;
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty!");
        }

        return data[queueStartIndex];
    }

    @Override
    public void clear() {
        Arrays.fill(data, null);
        queueEndIndex = -1;
        queueStartIndex = -1;
        elementCount = 0;
    }

    @Override
    public boolean isEmpty() {
        return elementCount == 0;
    }

    @Override
    public boolean isFull() {
        return elementCount == maxSize;
    }

    @Override
    public String showStructure() {
        StringBuilder elements = new StringBuilder();

        for (int i = 0; i < elementCount; i++) {
            elements.append(data[cycleIncrement(queueEndIndex + 1)]).append(" ");
        }

        return "CycleArrayQueue: \n" +
                "queueEndIndex: " + queueEndIndex + "\n" +
                "queueStartIndex: " + queueStartIndex + "\n" +
                "elementCount: " + elementCount + "\n" +
                "elements: " + elements;
    }
}
