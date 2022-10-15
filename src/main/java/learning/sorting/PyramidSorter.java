package learning.sorting;

import learning.pyramid.Pyramid;

import java.util.List;

public class PyramidSorter implements ListSorter {
    @Override
    public <T extends Comparable<T>> void sort(List<T> collection) {
        Pyramid<T> pyramid = Pyramid.fromList(collection);

        for (int i = collection.size() - 1; i >= 0 ; i--) {
            collection.set(i, pyramid.popWithoutClear());
        }
    }
}
