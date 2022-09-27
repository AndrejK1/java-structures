package learning.list;

import additional.ADT;

public interface List<T> extends ADT {
    T get(int position);

    boolean contains(T element);

    int size();

    int findPos(T element);
}
