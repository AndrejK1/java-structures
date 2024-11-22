package learning.leetcode;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Set<Integer>> numberPositions = new HashMap<>();

        for (int idx = 0; idx < nums.length; idx++) {
            int num = nums[idx];
            if (num < target) {
                numberPositions.putIfAbsent(num, new HashSet<>());
                numberPositions.merge(nums[idx], Collections.singleton(idx), (set1, set2) -> {
                    set1.addAll(set2);
                    return set1;
                });
            }
        }

        numberPositions.forEach((num, idx) -> {
            int secondNum = target - num;

            Set<Integer> positions = numberPositions.get(secondNum);

            if (positions == null) {
                return;
            }

            for (Integer position : positions) {
                if (!idx.contains(position)) {

                }
            }

            return new int[]{-1, -1};
        });

        return new int[]{-1, -1};
    }
}
