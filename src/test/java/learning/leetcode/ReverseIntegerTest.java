package learning.leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReverseIntegerTest {

    @Test
    void reverse() {
        ReverseInteger reverseInteger = new ReverseInteger();

        Assertions.assertEquals(0, reverseInteger.reverseV2(1534236469));
        Assertions.assertEquals(123, reverseInteger.reverseV2(321));
        Assertions.assertEquals(-123, reverseInteger.reverseV2(-321));
        Assertions.assertEquals(0, reverseInteger.reverseV2(-2147483648));
    }
}