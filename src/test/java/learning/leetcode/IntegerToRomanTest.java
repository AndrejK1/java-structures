package learning.leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegerToRomanTest {

    @Test
    void intToRoman() {
        Assertions.assertEquals("MMMDCCCXIX", new IntegerToRoman().intToRoman(3819));
        Assertions.assertEquals("LVIII", new IntegerToRoman().intToRoman(58));
    }
}