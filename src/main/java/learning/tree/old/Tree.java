package learning.tree.old;

import java.util.function.Consumer;

public interface Tree<T> {
    void add(T value);

    void remove(T value);

    void forEach(Consumer<T> action);

    void print();
}
