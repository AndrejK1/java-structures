package learning.leetcode;

public class ZigzagConversion {
    // rows = 5
    // length = 23

    // pad = 5 + 2 - 2 = 8
    // patternRepeats = 3
    //
    // rows
    // 0 - 0, 8, 16
    // 1 - 1, 7, 9, 15, 17, 23
    // 2 - 2, 6, 10, 14, 18, 22
    // 3 - 3, 5, 11, 13, 19, 21
    // 4 - 4, 12, 20



    public String convert(String s, int numRows) {
        int pad = Math.max(numRows * 2 - 2, 1);
        int patternRepeats = s.length() / pad + (s.length() % pad == 0 ? 0 : 1);

        StringBuilder output = new StringBuilder();

        for (int row = 0; row < numRows; row++) {
            for (int patternNum = 0; patternNum < patternRepeats; patternNum++) {
                if (row + patternNum * pad < s.length()) {
                    output.append(s.charAt(row + patternNum * pad));
                }

                if (row != 0 && row != numRows - 1 && pad - row + patternNum * pad < s.length()) {
                    output.append(s.charAt(pad - row + patternNum * pad));
                }
            }
        }

        return output.toString();
    }
}
