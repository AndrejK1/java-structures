package learning.leetcode;

public class RegularExpression {

    public boolean isMatchV2(String s, String p) {
        boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
        dp[0][0] = true;

        // 0 index in loop means empty string
        // 1 index in loop means 0 index in string
        for (int sIdx = 0; sIdx <= s.length(); sIdx++) {
            for (int pIdx = 1; pIdx <= p.length(); pIdx++) {

                if (p.charAt(pIdx - 1) == '*'
                        && (dp[sIdx][pIdx - 2] ||
                        (sIdx > 0 && dp[sIdx - 1][pIdx] && (s.charAt(sIdx - 1) == p.charAt(pIdx - 2) || p.charAt(pIdx - 2) == '.')))) {
                    dp[sIdx][pIdx] = true;
                } else if (sIdx > 0 && dp[sIdx - 1][pIdx - 1] && (p.charAt(pIdx - 1) == s.charAt(sIdx - 1) || p.charAt(pIdx - 1) == '.') ) {
                    dp[sIdx][pIdx] = true;
                }
            }
        }

        return dp[s.length()][p.length()];
    }

    public boolean isMatch(String s, String p) {
        int nextAsteriskIndex = p.indexOf('*');
        boolean asteriskMode = false;

        int sIdx = 0;
        int pIdx = 0;

        while (sIdx < s.length()) {
            if (pIdx >= p.length()) {
                return false;
            }

            int sChar = s.charAt(sIdx);
            int pChar = p.charAt(pIdx);

            boolean dotMode = pChar == '.';

            if (pIdx + 1 == nextAsteriskIndex) {
                asteriskMode = true;
            }

            if (asteriskMode) {
                if (dotMode) {
                    break;
                }

                if (pChar != sChar) {
                    pIdx += 2;
                    nextAsteriskIndex = p.indexOf('*', pIdx);
                    asteriskMode = false;
                } else {
                    sIdx += 1;
                }

                continue;
            } else if (!dotMode && pChar != sChar) {
                return false;
            }

            pIdx++;
            sIdx++;
        }

        if (asteriskMode) {
            pIdx += 2;
        }

        return pIdx >= p.length();
    }

    private static String toString(boolean[][] dp, String p, String s) {
        StringBuilder str = new StringBuilder("    ");

        for (int i = 0; i < p.length(); i++) {
            str.append(p.charAt(i)).append(" ");
        }

        str.append('\n');

        for (int i = 0; i < dp.length; i++) {
            if (i == 0) {
                str.append(" ");
            } else {
                str.append(s.charAt(i - 1));
            }
            str.append(" ");
            for (int j = 0; j < dp[0].length; j++) {
                str.append(dp[i][j] ? "T" : "-").append(" ");
            }
            str.append('\n');
        }

        return str.toString();
    }
}
