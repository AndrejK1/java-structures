package learning.leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RegularExpressionTest {

    @Test
    void isMatch() {
        RegularExpression regularExpression = new RegularExpression();
        Assertions.assertTrue(regularExpression.isMatchV2("aaaaa", "aa*aa"));

        Assertions.assertTrue(regularExpression.isMatch("aaaaa", "aa*aa"));
        Assertions.assertTrue(regularExpression.isMatch("mississippi", "mis*is*ip*."));

        Assertions.assertTrue(regularExpression.isMatch("aa", "a*"));
        Assertions.assertTrue(regularExpression.isMatch("aa", ".*"));
        Assertions.assertTrue(regularExpression.isMatch("aa", ".a"));
        Assertions.assertTrue(regularExpression.isMatch("aa", "a."));
        Assertions.assertTrue(regularExpression.isMatch("aa", ".."));

        Assertions.assertFalse(regularExpression.isMatch("aa", "a"));
        Assertions.assertFalse(regularExpression.isMatch("aa", "ab"));
        Assertions.assertFalse(regularExpression.isMatch("aa", "b*"));
        Assertions.assertFalse(regularExpression.isMatch("aa", ".b"));
        Assertions.assertFalse(regularExpression.isMatch("aa", "b."));
    }

    @Test
    void isMatchV2() {
        RegularExpression regularExpression = new RegularExpression();

        Assertions.assertTrue(regularExpression.isMatchV2("aa", "a*"));
        Assertions.assertTrue(regularExpression.isMatchV2("aa", ".*"));
        Assertions.assertTrue(regularExpression.isMatchV2("aa", ".a"));
        Assertions.assertTrue(regularExpression.isMatchV2("aa", "a."));
        Assertions.assertTrue(regularExpression.isMatchV2("aa", ".."));

        Assertions.assertFalse(regularExpression.isMatchV2("aa", "a"));
        Assertions.assertFalse(regularExpression.isMatchV2("aa", "ab"));
        Assertions.assertFalse(regularExpression.isMatchV2("aa", "b*"));
        Assertions.assertFalse(regularExpression.isMatchV2("aa", ".b"));
        Assertions.assertFalse(regularExpression.isMatchV2("aa", "b."));

        Assertions.assertTrue(regularExpression.isMatchV2("mississippi", "mis*is*ip*."));
        Assertions.assertFalse(regularExpression.isMatchV2("mississippi", "mis*is*p*."));
        Assertions.assertTrue(regularExpression.isMatchV2("abcaaabca", "aa*.*a*b.a"));
        Assertions.assertTrue(regularExpression.isMatchV2("aaaaa", "aa*aa"));

    }
}