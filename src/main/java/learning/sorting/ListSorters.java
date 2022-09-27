package learning.sorting;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ListSorters {
    public static final ListSorter BUBBLE_SORTER = new BubbleSorter();
    public static final ListSorter INSERTION_SORTER = new InsertionSorter();
    public static final ListSorter SELECTION_SORTER = new SelectionSorter();
    public static final ListSorter MERGE_SORTER = new MergeSorter();
    public static final ListSorter SHELL_SORTER = new ShellSorter();
    public static final ListSorter QUICK_SORTER = new QuickSorter();
}
