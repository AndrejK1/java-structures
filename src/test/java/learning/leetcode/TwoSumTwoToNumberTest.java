package learning.leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TwoSumTwoToNumberTest {

    @Test
    void twoSum() {
        SumTwoToNumber twoSum = new SumTwoToNumber();

        int[] idx = twoSum.twoSumV1(new int[]{2, 7, 11, 15}, 9);

        Assertions.assertEquals(0, idx[0]);
        Assertions.assertEquals(1, idx[1]);

        int[] idxv2 = twoSum.twoSumV2(new int[]{2, 7, 11, 15}, 9);

        Assertions.assertEquals(1, idxv2[0]);
        Assertions.assertEquals(0, idxv2[1]);
    }
}