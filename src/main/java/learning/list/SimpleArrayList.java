package learning.list;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SimpleArrayList<T> implements MutableList<T> {
    private final float sizeMultiplier;
    private Object[] elements;
    private int size;

    public SimpleArrayList() {
        this(16, 1.5f);
    }

    public SimpleArrayList(int size) {
        this(size, 1.5f);
    }

    public SimpleArrayList(int size, float sizeMultiplier) {
        this.sizeMultiplier = sizeMultiplier;
        elements = new Object[size];
    }

    @Override
    public void add(T element) {
        adjustCapacity();
        elements[size] = element;
        size++;
    }

    @Override
    public void add(T element, int position) {
        adjustCapacity();
        shiftArrayRight(position);
        elements[position] = element;
        size++;
    }

    @Override
    public void set(T element, int position) {
        checkSize(position);
        elements[position] = element;
    }

    @Override
    public T get(int position) {
        checkSize(position);
        return (T) elements[position];
    }

    @Override
    public T remove(int position) {
        T element = get(position);
        shiftArrayLeft(position);
        elements[--size] = null;
        return element;
    }

    @Override
    public boolean remove(T element) {
        int pos = findPos(element);

        if (pos == -1) {
            return false;
        }

        shiftArrayLeft(pos);
        elements[--size] = null;

        return true;
    }

    @Override
    public void clear() {
        elements = new Object[16];
        size = 0;
    }

    @Override
    public boolean contains(T element) {
        return findPos(element) != -1;
    }

    @Override
    public int findPos(T element) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(element)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String showStructure() {
        return "ArrayList:\n" +
                "size: " + size + "\n" +
                "array size: " + elements.length + "\n" +
                "size multiplier: " + sizeMultiplier + "\n" +
                "elements: " + Arrays.stream(elements).map(String::valueOf).collect(Collectors.joining(","));
    }

    private void shiftArrayLeft(int pos) {
        for (int i = pos; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
    }

    private void shiftArrayRight(int pos) {
        for (int i = size; i > pos; i--) {
            elements[i] = elements[i - 1];
        }
    }

    private void checkSize(int position) {
        if (position < 0 || position >= size) {
            throw new IllegalArgumentException("position " + position + " is not valid");
        }
    }

    private void adjustCapacity() {
        if (size + 1 <= elements.length) {
            return;
        }

        Object[] currentElements = elements;
        elements = new Object[(int) Math.ceil(elements.length * sizeMultiplier)];
        System.arraycopy(currentElements, 0, elements, 0, currentElements.length);
    }
}
