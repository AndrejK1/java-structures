package learning.leetcode;

import java.util.Map;

public class IntegerToRoman {
    private static final Map<Integer, Character> ROMANIAN_MAP = Map.ofEntries(
            Map.entry(1, 'I'),
            Map.entry(5, 'V'),
            Map.entry(10, 'X'),
            Map.entry(50, 'L'),
            Map.entry(100, 'C'),
            Map.entry(500, 'D'),
            Map.entry(1000, 'M')
    );

    public String intToRoman(int num) {
        int numDigits = (int) Math.log10(num) + 1;
        int i;

        StringBuilder roman = new StringBuilder();

        for (int numPos = numDigits - 1; numPos >= 0; numPos--) {
            int current10pow = (int) Math.pow(10, numPos);
            int currentDigit = num % (current10pow * 10) / current10pow;

            if (currentDigit == 9 || currentDigit == 4) {
                roman.append(ROMANIAN_MAP.get(current10pow));
                roman.append(ROMANIAN_MAP.get(current10pow * (currentDigit + 1)));
                continue;
            }

            if (currentDigit >= 5) {
                roman.append(ROMANIAN_MAP.get(current10pow * 5));
            }

            int remainingOnes = currentDigit % 5;

            for (i = 0; i < remainingOnes; i++) {
                roman.append(ROMANIAN_MAP.get(current10pow));
            }
        }

        return roman.toString();
    }
}
