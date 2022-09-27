package learning.sorting;

import java.util.List;

public class QuickSorter implements ListSorter {

    @Override
    public <T extends Comparable<T>> void sort(List<T> collection) {
        sortPartition(collection, 0, collection.size() - 1);
    }

    private <T extends Comparable<T>> void sortPartition(List<T> collection, int start, int end) {
        if (end - start + 1 <= 10) {
            insertionSort(collection, start, end);
            return;
        }

        int newPosition = partition(collection, start, end);
        sortPartition(collection, start, newPosition);
        sortPartition(collection, newPosition + 1, end);
    }

    private <T extends Comparable<T>> int partition(List<T> collection, int start, int end) {
        T median = prepareMedian(collection, start, end);

        int startPrt = start + 1;
        int endPrt = end - 1;

        while (true) {
            while (startPrt < end && median.compareTo(collection.get(startPrt)) > 0) {
                startPrt++;
            }

            while (endPrt > start && median.compareTo(collection.get(endPrt)) < 0) {
                endPrt--;
            }

            if (startPrt >= endPrt) {
                break;
            }

            collection.set(startPrt, collection.set(endPrt, collection.get(startPrt)));

            startPrt++;
            endPrt--;
        }
        return endPrt;
    }

    private <T extends Comparable<T>> void insertionSort(List<T> collection, int start, int end) {
        int outer;
        int inner;
        T temp;

        for (outer = start + 1; outer <= end; outer++) {
            temp = collection.get(outer);
            inner = outer;

            while (inner > start && collection.get(inner - 1).compareTo(temp) >= 0) {
                collection.set(inner, collection.get(inner - 1));
                inner--;
            }

            collection.set(inner, temp);
        }
    }

    private <T extends Comparable<T>> T prepareMedian(List<T> collection, int start, int end) {
        int mid = (start + end) / 2;

        if (collection.get(start).compareTo(collection.get(mid)) > 0) {
            collection.set(start, collection.set(mid, collection.get(start)));
        }

        if (collection.get(start).compareTo(collection.get(end)) > 0) {
            collection.set(start, collection.set(end, collection.get(start)));
        }

        if (collection.get(mid).compareTo(collection.get(end)) > 0) {
            collection.set(mid, collection.set(end, collection.get(mid)));
        }

        return collection.get(mid);
    }
}
