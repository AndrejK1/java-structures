package learning.sorting;

import java.util.ArrayList;
import java.util.List;

public class MergeSorter implements ListSorter {
    @Override
    public <T extends Comparable<T>> void sort(List<T> collection) {
        List<T> buffer = new ArrayList<>(collection);
        sort(collection, buffer, 0, collection.size() - 1);
    }

    private <T extends Comparable<T>> void sort(List<T> originalCollection, List<T> buffer, int bottom, int top) {
        if (top == bottom) {
            return;
        }

        int mid = (bottom + top) / 2;

        sort(originalCollection, buffer, bottom, mid);
        sort(originalCollection, buffer, mid + 1, top);
        merge(originalCollection, buffer, bottom, mid + 1, top);
    }

    private <T extends Comparable<T>> void merge(List<T> originalCollection, List<T> buffer,
                                                 final int bottom, final int mid, final int top) {
        int bottomPtr = bottom;
        int midPointer = mid;
        int insertOffset = bottom;

        while (bottomPtr < mid && midPointer <= top) {
            T first = originalCollection.get(bottomPtr);
            T second = originalCollection.get(midPointer);

            if (first.compareTo(second) > 0) {
                buffer.set(insertOffset, second);
                midPointer++;
            } else {
                buffer.set(insertOffset, first);
                bottomPtr++;
            }

            insertOffset++;
        }

        while (midPointer <= top) {
            buffer.set(insertOffset++, originalCollection.get(midPointer++));
        }

        while (bottomPtr < mid) {
            buffer.set(insertOffset++, originalCollection.get(bottomPtr++));
        }

        for (int i = bottom; i <= top; i++) {
            originalCollection.set(i, buffer.get(i));
        }
    }
}
