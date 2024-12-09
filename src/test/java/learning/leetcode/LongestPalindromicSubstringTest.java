package learning.leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LongestPalindromicSubstringTest {

    @Test
    void longestPalindrome() {
        LongestPalindromicSubstring longestPalindromicSubstring = new LongestPalindromicSubstring();
        String s = longestPalindromicSubstring.longestPalindrome("cbbd");
        Assertions.assertEquals("bb", s);
    }
}