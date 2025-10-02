package learning.leetcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SortedArrayMedianTest {

    @Test
    void findMedianSortedEmptyArrays() {
        SortedArrayMedian sortedArrayMedian = new SortedArrayMedian();

        // empty
        assertEquals(1d, sortedArrayMedian.findMedianSortedArrays(new int[]{}, new int[]{1}));
        assertEquals(2d, sortedArrayMedian.findMedianSortedArrays(new int[]{}, new int[]{1, 3}));
        assertEquals(1d, sortedArrayMedian.findMedianSortedArrays(new int[]{1}, new int[]{}));
        assertEquals(2d, sortedArrayMedian.findMedianSortedArrays(new int[]{1, 3}, new int[]{}));
    }

    @Test
    void findMedianSortedSingleArrays() {
        SortedArrayMedian sortedArrayMedian = new SortedArrayMedian();
        // single
        assertEquals(1d, sortedArrayMedian.findMedianSortedArrays(new int[]{1}, new int[]{1}));
        assertEquals(2d, sortedArrayMedian.findMedianSortedArrays(new int[]{1}, new int[]{3}));
        assertEquals(1d, sortedArrayMedian.findMedianSortedArrays(new int[]{3}, new int[]{-1}));
    }

    @Test
    void findMedianSortedNonIntersectingArrays() {
        SortedArrayMedian sortedArrayMedian = new SortedArrayMedian();
        // non-intersecting
        assertEquals(55d, sortedArrayMedian.findMedianSortedArrays(new int[]{-1, 10}, new int[]{100, 200}));
        assertEquals(55d, sortedArrayMedian.findMedianSortedArrays(new int[]{100, 200}, new int[]{-1, 10}));
        assertEquals(50d, sortedArrayMedian.findMedianSortedArrays(new int[]{-1, 23}, new int[]{50, 100, 200}));
        assertEquals(50d, sortedArrayMedian.findMedianSortedArrays(new int[]{50, 100, 200}, new int[]{-1, 23}));
        assertEquals(-62d, sortedArrayMedian.findMedianSortedArrays(new int[]{-1, 23}, new int[]{-200, -123}));
        assertEquals(-62d, sortedArrayMedian.findMedianSortedArrays(new int[]{-200, -123}, new int[]{-1, 23}));
        assertEquals(-123d, sortedArrayMedian.findMedianSortedArrays(new int[]{-1, 23}, new int[]{-200, -145, -123}));
        assertEquals(-123d, sortedArrayMedian.findMedianSortedArrays(new int[]{-200, -145, -123}, new int[]{-1, 23}));

        assertEquals(6d, sortedArrayMedian.findMedianSortedArrays(new int[]{1, 2}, new int[]{3, 4, 5, 6, 7, 8, 9, 10, 11}));
        assertEquals(6d, sortedArrayMedian.findMedianSortedArrays(new int[]{3, 4, 5, 6, 7, 8, 9, 10, 11}, new int[]{1, 2}));
        assertEquals(6.5d, sortedArrayMedian.findMedianSortedArrays(new int[]{1, 2}, new int[]{3, 4, 5, 6, 7, 8, 9, 10, 11, 12}));
        assertEquals(6.5d, sortedArrayMedian.findMedianSortedArrays(new int[]{3, 4, 5, 6, 7, 8, 9, 10, 11, 12}, new int[]{1, 2}));
    }

    @Test
    void findMedianSortedIntersectingArrays() {
        SortedArrayMedian sortedArrayMedian = new SortedArrayMedian();
        // intersecting
        assertEquals(4d, sortedArrayMedian.findMedianSortedArrays(new int[]{2, 10}, new int[]{1, 6}));
        assertEquals(4d, sortedArrayMedian.findMedianSortedArrays(new int[]{1, 6}, new int[]{2, 10}));
        assertEquals(15d, sortedArrayMedian.findMedianSortedArrays(new int[]{2, 10, 12, 14, 16, 18, 20, 22, 24, 26}, new int[]{1, 6}));
        assertEquals(15d, sortedArrayMedian.findMedianSortedArrays(new int[]{1, 6}, new int[]{2, 10, 12, 14, 16, 18, 20, 22, 24, 26}));
        assertEquals(16d, sortedArrayMedian.findMedianSortedArrays(new int[]{2, 10, 12, 14, 16, 18, 19, 20, 22, 24, 26}, new int[]{1, 6}));
        assertEquals(16d, sortedArrayMedian.findMedianSortedArrays(new int[]{1, 6}, new int[]{2, 10, 12, 14, 16, 18, 19, 20, 22, 24, 26}));

        assertEquals(6d, sortedArrayMedian.findMedianSortedArrays(new int[]{2, 10}, new int[]{1, 60}));
        assertEquals(6d, sortedArrayMedian.findMedianSortedArrays(new int[]{1, 60}, new int[]{2, 10}));
        assertEquals(17d, sortedArrayMedian.findMedianSortedArrays(new int[]{1, 60}, new int[]{2, 10, 12, 14, 16, 18, 20, 22, 24, 26}));
        assertEquals(18d, sortedArrayMedian.findMedianSortedArrays(new int[]{1, 60}, new int[]{2, 10, 12, 14, 16, 18, 19, 20, 22, 24, 26}));
    }
}