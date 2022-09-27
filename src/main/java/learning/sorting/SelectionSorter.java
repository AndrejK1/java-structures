package learning.sorting;

import java.util.List;

public class SelectionSorter implements ListSorter {
    @Override
    public <T extends Comparable<T>> void sort(List<T> collection) {
        int outer;
        int inner;
        int min;

        for (outer = 0; outer < collection.size() - 1; outer++) {
            min = outer;

            for (inner = outer + 1; inner < collection.size(); inner++) {
                if (collection.get(min).compareTo(collection.get(inner)) > 0) {
                    min = inner;
                }
            }

            T temp = collection.get(outer);
            collection.set(outer, collection.get(min));
            collection.set(min, temp);
        }
    }
}
