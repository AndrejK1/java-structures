package learning.leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LongestStrWithoutRepeatingCharTest {

    @Test
    void lengthOfLongestSubstring() {
        assertResult("", 0);
        assertResult("a", 1);
        assertResult("ab", 2);
        assertResult("ab", 2);
        assertResult("aab", 2);
        assertResult("abb", 2);
        assertResult("aabb", 2);
        assertResult("cabb", 3);
        assertResult("bcab", 3);
        assertResult("bcabc", 3);
        assertResult("abcabc", 3);
        assertResult("abbacc", 3);
        assertResult("aababcc", 3);
        assertResult("abacc", 3);
    }

    void assertResult(String input, int expected) {
        Assertions.assertEquals(expected, new LongestStrWithoutRepeatingChar().lengthOfLongestSubstring(input));

    }
}