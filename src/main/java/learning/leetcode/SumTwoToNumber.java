package learning.leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SumTwoToNumber {
    public int[] twoSumV2(int[] nums, int target) {
        Map<Integer, Integer> numberPositions = new HashMap<>();

        for (int idx = 0; idx < nums.length; idx++) {
            int num = nums[idx];
            int secondNum = target - num;

            if (numberPositions.containsKey(secondNum)) {
                return new int[]{idx, numberPositions.get(secondNum)};
            }

            numberPositions.put(num, idx);
        }

        return new int[]{-1, -1};
    }


    public int[] twoSumV1(int[] nums, int target) {
        Map<Integer, List<Integer>> numberPositions = new HashMap<>();

        for (int idx = 0; idx < nums.length; idx++) {
            int num = nums[idx];

            numberPositions.putIfAbsent(num, new ArrayList<>());
            numberPositions.merge(nums[idx], Collections.singletonList(idx), (set1, set2) -> {
                set1.addAll(set2);
                return set1;
            });

        }

        for (Map.Entry<Integer, List<Integer>> numberWithPositions : numberPositions.entrySet()) {
            Integer secondNumber = numberWithPositions.getKey();
            int secondNum = target - secondNumber;

            int firstIdx = numberWithPositions.getValue().getFirst();
            int secondIdxPos = secondNumber.equals(secondNum) ? 1 : 0;

            List<Integer> secondNumberPositions = numberPositions.get(secondNum);

            if (secondNumberPositions == null || secondNumberPositions.size() < secondIdxPos + 1) {
                continue;
            }

            return new int[]{firstIdx, secondNumberPositions.get(secondIdxPos)};
        }

        return new int[]{-1, -1};
    }
}
