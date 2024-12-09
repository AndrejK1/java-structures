package learning.leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ZigzagConversionTest {

    @Test
    void convert3Rows() {
        ZigzagConversion zigzagConversion = new ZigzagConversion();
        String paypalishiring = zigzagConversion.convert("PAYPALISHIRING", 3);
        Assertions.assertEquals("PAHNAPLSIIGYIR", paypalishiring);
    }

    @Test
    void convert4Rows() {
        ZigzagConversion zigzagConversion = new ZigzagConversion();
        String paypalishiring = zigzagConversion.convert("PAYPALISHIRING", 4);
        Assertions.assertEquals("PINALSIGYAHRPI", paypalishiring);
    }
}