package learning.leetcode;

import java.util.HashMap;
import java.util.Map;

public class ContainerWithWater {
    public int maxArea(int[] height) {
        int maxHeight = Integer.MIN_VALUE;
        Map<Integer, int[]> boundsByHeight = new HashMap<>();

        for (int idx = 0; idx < height.length; idx++) {
            int currHeight = height[idx];

            maxHeight = Math.max(maxHeight, currHeight);

            int[] currHeightBounds = boundsByHeight.get(currHeight);

            if (currHeightBounds == null) {
                boundsByHeight.put(currHeight, new int[]{idx, idx});
            } else {
                currHeightBounds[1] = Math.max(currHeightBounds[1], idx);
            }
        }
        
        int maxArea = 0;

        for (int heightToCheck = maxHeight; heightToCheck > 0 ; heightToCheck--) {

            if (height.length * heightToCheck < maxArea) {
                break;
            }

            int[] currentBounds = boundsByHeight.get(heightToCheck);

            if (currentBounds == null) {
                continue;
            }

            maxArea = Math.max(maxArea, (currentBounds[1] - currentBounds[0]) * heightToCheck);

            int[] nextBounds = boundsByHeight.get(heightToCheck - 1);

            if (nextBounds == null) {
                nextBounds = currentBounds;
            } else {
                nextBounds[0] = Math.min(nextBounds[0], currentBounds[0]);
                nextBounds[1] = Math.max(nextBounds[1], currentBounds[1]);
            }

            boundsByHeight.put(heightToCheck - 1, nextBounds);

        }
        
        return maxArea;
    }
}
