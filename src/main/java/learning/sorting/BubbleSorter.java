package learning.sorting;

import java.util.List;

public class BubbleSorter implements ListSorter {
    @Override
    public <T extends Comparable<T>> void sort(List<T> collection) {
        int outer;
        int inner;

        for (outer = collection.size() - 1; outer > 0; outer--) {
            for (inner = 0; inner < outer; inner++) {
                if (collection.get(inner).compareTo(collection.get(inner + 1)) > 0) {
                    T temp = collection.get(inner);
                    collection.set(inner, collection.get(inner + 1));
                    collection.set(inner + 1, temp);
                }
            }
        }
    }
}
