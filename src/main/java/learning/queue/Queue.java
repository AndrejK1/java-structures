package learning.queue;

import additional.ADT;

public interface Queue<T> extends ADT {
    void push(T element);

    T pop();

    T peek();

    void clear();

    boolean isEmpty();

    boolean isFull();
}
