package learning.leetcode;

import java.util.HashMap;
import java.util.Map;

public class LongestStrWithoutRepeatingChar {

    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        if (s.length() == 1) {
            return 1;
        }

        Map<Character, Integer> charPositions = new HashMap<>();
        int max = 0;
        int lastCollision = -1;

        for (int idx = 0; idx < s.length(); idx++) {
            char character = s.charAt(idx);

            Integer previousPosition = charPositions.get(character);

            if (previousPosition != null) {
                if (lastCollision == -1) {
                    max = idx;
                } else {
                    max = Math.max(max, idx - lastCollision - 1);
                }

                lastCollision = Math.max(previousPosition, lastCollision);
            }

            charPositions.put(character, idx);
        }

        return max == 0 ? s.length() : Math.max(max, s.length() - lastCollision - 1);
    }
}
