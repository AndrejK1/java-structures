package learning.leetcode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReverseInteger {
    public int reverse(int x) {
        StringBuilder reversedStr = new StringBuilder(String.valueOf(x)).reverse();

        if (x < 0) {
            reversedStr.deleteCharAt(reversedStr.length() - 1);
            reversedStr.insert(0, '-');
        }

        long reversed = Long.parseLong(reversedStr.toString());

        if (reversed > Integer.MAX_VALUE || reversed < Integer.MIN_VALUE) {
            return 0;
        }

        return (int) reversed;
    }

    public int reverseV2(int x) {
        int xLength = String.valueOf(x).length();

        if (x < 0) {
            xLength -= 1;
        }

        long result = 0;
        int divider = 1;

        for (int i = 0; i < xLength; i++) {
            int nextDigit = x / divider % 10;

            result += (long) (nextDigit * Math.pow(10, xLength - i - 1));

            if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
                return 0;
            }

            divider *= 10;
        }

        return (int) result;
    }
}
