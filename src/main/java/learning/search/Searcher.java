package learning.search;

import java.util.List;

public interface Searcher {
    <T extends Comparable<T>> int findPosition(List<T> list, T element);
}
