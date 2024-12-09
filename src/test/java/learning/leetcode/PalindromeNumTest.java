package learning.leetcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PalindromeNumTest {

    @Test
    void isPalindrome() {
        PalindromeNum palindromeNum = new PalindromeNum();
        boolean palindrome = palindromeNum.isPalindrome(121);
        assertTrue(palindrome);
    }

    @Test
    void isNotPalindrome() {
        PalindromeNum palindromeNum = new PalindromeNum();
        boolean palindrome = palindromeNum.isPalindrome(1211);
        assertFalse(palindrome);
    }

    @Test
    void negativeIsNotPalindrome() {
        PalindromeNum palindromeNum = new PalindromeNum();
        boolean palindrome = palindromeNum.isPalindrome(-1211);
        assertFalse(palindrome);
    }
}