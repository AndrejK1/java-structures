package learning.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * SpiralMatrix.java
 *
 * @author Andrii Kononenko
 * @since 07.10.25
 */
public class SpiralMatrix {
    public List<Integer> spiralOrderV1(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int max = Math.max(m, n) / 2 + 1;
        int size = n * m;
        int offset = 0;

        List<Integer> res = new ArrayList<>();

        while (offset < max) {
            for (int i = offset; i < n - offset; i++) {
                res.add(matrix[offset][i]);
            }

            if (size == res.size()) {
                break;
            }

            for (int i = offset + 1; i < m - offset - 1; i++) {
                res.add(matrix[i][n - 1 - offset]);
            }

            if (size == res.size()) {
                break;
            }

            for (int i = n - offset - 1; i >= offset; i--) {
                res.add(matrix[m - 1 - offset][i]);
            }

            if (size == res.size()) {
                break;
            }

            for (int i = m - 2 - offset; i >= offset + 1; i--) {
                res.add(matrix[i][offset]);
            }

            if (size == res.size()) {
                break;
            }

            offset++;
        }

        return res;
    }
}
