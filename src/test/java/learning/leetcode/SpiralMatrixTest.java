package learning.leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;


class SpiralMatrixTest {

    @Test
    void spiralOrderV1() {
        List<Integer> integers = new SpiralMatrix().spiralOrderV1(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        Assertions.assertEquals(Arrays.asList(1, 2, 3, 6, 9, 8, 7, 4, 5), integers);
    }
}