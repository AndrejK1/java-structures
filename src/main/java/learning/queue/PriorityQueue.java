package learning.queue;

public interface PriorityQueue<K extends Comparable<K>, T> extends Queue<T> {

    void push(T element, K priority);

    boolean remove(T element);
}
