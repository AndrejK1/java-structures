package learning.queue;

public interface PriorityQueue<T> extends Queue<T> {

    void push(T element, int priority);

}
