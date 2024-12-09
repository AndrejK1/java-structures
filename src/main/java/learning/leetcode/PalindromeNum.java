package learning.leetcode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PalindromeNum {
    public boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }

        int xCopy = x;
        int xReversed = 0;

        while (xCopy >= 1) {
            xReversed = xReversed * 10 + xCopy % 10;
            xCopy /= 10;
        }

        return x == xReversed;
    }
}
