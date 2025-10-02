package learning.leetcode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SortedArrayMedian {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;

        int medianIdx = (m + n) / 2;
        boolean even = (m + n) % 2 == 0;
        int median = 0;

        int mIdx = 0;
        int nIdx = 0;

        while (nIdx + mIdx <= medianIdx) {
            int small;

            int mVal = mIdx >= nums1.length ? Integer.MAX_VALUE : nums1[mIdx];
            int nVal = nIdx >= nums2.length ? Integer.MAX_VALUE : nums2[nIdx];

            if (mVal < nVal) {
                mIdx++;
                small = mVal;
            } else {
                nIdx++;
                small = nVal;
            }

            if (even && nIdx + mIdx == medianIdx) {
                median += small;
            }

            if (nIdx + mIdx == medianIdx + 1) {
                median += small;
            }
        }

        return even ? median / 2.0 : median;
    }
}
