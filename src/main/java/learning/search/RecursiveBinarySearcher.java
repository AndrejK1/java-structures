package learning.search;

import java.util.List;

public class RecursiveBinarySearcher implements Searcher {
    @Override
    public <T extends Comparable<T>> int findPosition(List<T> list, T element) {
        return findRecursively(list, element, 0, list.size() - 1);
    }

    private <T extends Comparable<T>> int findRecursively(List<T> list, T element, int lowerBound, int upperBound) {
        if (lowerBound > upperBound) {
            return -1;
        }

        int position = (lowerBound + upperBound) / 2;

        if (list.get(position).compareTo(element) == 0) {
            return position;
        }

        if (list.get(position).compareTo(element) > 0) {
            return findRecursively(list, element, lowerBound, position - 1);
        } else {
            return findRecursively(list, element, position + 1, upperBound);
        }
    }
}
