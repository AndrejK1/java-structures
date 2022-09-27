package learning.list;

public interface MutableList<T> extends List<T> {
    void add(T element);

    void add(T element, int position);

    T remove(int position);

    boolean remove(T element);

    void clear();

}