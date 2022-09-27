package learning.stack;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class LinkedStack<T> implements Stack<T> {
    private final List<T> data;
    private int top;

    public LinkedStack() {
        this(Collections.emptyList());
    }

    public LinkedStack(Collection<T> data) {
        this.data = new LinkedList<>(data);
        top = data.size() - 1;
    }

    @Override
    public void push(T element) {
        data.add(element);
        top++;
    }

    @Override
    public T pop() {
        return data.remove(top--);
    }

    @Override
    public T peek() {
        return data.get(top);
    }

    @Override
    public void clear() {
        data.clear();
        top = -1;
    }

    @Override
    public boolean isEmpty() {
        return top == -1;
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public String showStructure() {
        StringBuilder elements = new StringBuilder();
        data.forEach(e -> elements.append(e).append(" "));

        return "LinkedStack: \n" +
                "top: " + top + "\n" +
                "elements: " + elements;
    }
}
