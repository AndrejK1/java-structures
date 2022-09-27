package learning.sorting;

import java.util.List;

public class ShellSorter implements ListSorter {
    @Override
    public <T extends Comparable<T>> void sort(List<T> collection) {
        int h = 1;
        int n = collection.size();

        while (h < n / 3) {
            h = h * 3 + 1;
        }

        int sortPointer;
        int insertPointer;
        T temp;

        while (h > 0) {
            for (sortPointer = h; sortPointer < n; sortPointer++) {
                insertPointer = sortPointer;
                temp = collection.get(insertPointer);

                while (insertPointer > h - 1 && collection.get(insertPointer - h).compareTo(temp) >= 0) {
                    collection.set(insertPointer, collection.get(insertPointer - h));
                    insertPointer -= h;
                }

                collection.set(insertPointer, temp);
            }

            h = (h - 1) / 3;
        }
    }
}
