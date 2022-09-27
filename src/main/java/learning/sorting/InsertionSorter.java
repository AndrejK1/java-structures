package learning.sorting;

import java.util.List;

public class InsertionSorter implements ListSorter {
    @Override
    public <T extends Comparable<T>> void sort(List<T> collection) {
        int outer;
        int inner;

        for (outer = 1; outer < collection.size(); outer++) {
            T temp = collection.get(outer);
            inner = outer;

            while (inner > 0 && collection.get(inner - 1).compareTo(temp) >= 0) {
                collection.set(inner, collection.get(inner - 1));
                inner--;
            }

            collection.set(inner, temp);
        }
    }
}
