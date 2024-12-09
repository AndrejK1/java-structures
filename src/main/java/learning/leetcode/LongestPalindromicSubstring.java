package learning.leetcode;

public class LongestPalindromicSubstring {
    // b
    // bb
    // bab
    // baab
    // bacab

    public String longestPalindrome(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }

        int maxPalindromeLength = 1;
        int maxPalindromeStartIdx = 0;

        int startScanPos = 0;
        int endScanPos = 0;

        for (int i = 0; i < s.length() - 1; i++) {
            startScanPos = i;
            endScanPos = i;

            for (; startScanPos >= 0 && endScanPos < s.length(); startScanPos--, endScanPos++) {
                if (s.charAt(startScanPos) != s.charAt(endScanPos)) {
                    break;
                }

                if (maxPalindromeLength < endScanPos - startScanPos + 1) {
                    maxPalindromeLength = endScanPos - startScanPos + 1;
                    maxPalindromeStartIdx = startScanPos;
                }
            }

            startScanPos = i;
            endScanPos = i + 1;

            for (; startScanPos >= 0 && endScanPos < s.length(); startScanPos--, endScanPos++) {
                if (s.charAt(startScanPos) != s.charAt(endScanPos)) {
                    break;
                }

                if (maxPalindromeLength < endScanPos - startScanPos + 1) {
                    maxPalindromeLength = endScanPos - startScanPos + 1;
                    maxPalindromeStartIdx = startScanPos;
                }
            }
        }

        return s.substring(maxPalindromeStartIdx, maxPalindromeStartIdx + maxPalindromeLength);
    }
}
