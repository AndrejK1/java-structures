package learning.search;

import java.util.List;

public class BinarySearcher implements Searcher {
    @Override
    public <T extends Comparable<T>> int findPosition(List<T> list, T element) {
        int start = 0;
        int end = list.size() - 1;

        while (true) {
            if (start > end) {
                return -1;
            }

            int position = (start + end) / 2;

            if (list.get(position).compareTo(element) == 0) {
                return position;
            }

            if (list.get(position).compareTo(element) > 0) {
                end = position - 1;
            } else {
                start = position + 1;
            }
        }

    }
}
