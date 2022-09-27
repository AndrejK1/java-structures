package learning.sorting;

import java.util.List;

public class QuickSorter implements ListSorter {

    @Override
    public <T extends Comparable<T>> void sort(List<T> collection) {
        sortPartition(collection, 0, collection.size() - 1);
    }

    private <T extends Comparable<T>> void sortPartition(List<T> collection, int start, int end) {
        if (start >= end) {
            return;
        }

        int startPrt = start;
        int endPrt = end;
        T median = collection.get((start + end) / 2);
        T buffer;

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

            buffer = collection.get(startPrt);
            collection.set(startPrt, collection.get(endPrt));
            collection.set(endPrt, buffer);

            startPrt++;
            endPrt--;
        }

        sortPartition(collection, start, endPrt);
        sortPartition(collection, endPrt + 1, end);
    }
}
