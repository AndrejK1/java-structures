package learning.queue;

import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
public class ComparablePriorityQueue<T> implements Queue<T> {
    private final java.util.Queue<T> list = new LinkedList<>();
    private final Comparator<T> comparator;

    @Override
    public void push(T element) {
        int i = 0;

        List<T> local = (List<T>) list;

        while (i < list.size() && comparator.compare(local.get(i), element) < 0) {
            i++;
        }

        local.add(i, element);
    }

    @Override
    public T pop() {
        return list.poll();
    }

    @Override
    public T peek() {
        return list.peek();
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public String showStructure() {
        return list.toString();
    }

    public int size() {
        return list.size();
    }
}
