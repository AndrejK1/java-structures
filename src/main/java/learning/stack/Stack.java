package learning.stack;

import additional.ADT;

public interface Stack<T> extends ADT {
    void push(T element);

    T pop();

    T peek();

    void clear();

    boolean isEmpty();

    boolean isFull();
}
