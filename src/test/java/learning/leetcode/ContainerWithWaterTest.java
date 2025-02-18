package learning.leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContainerWithWaterTest {

    @Test
    void maxArea() {
        ContainerWithWater containerWithWater = new ContainerWithWater();

        int area = containerWithWater.maxArea(new int[]{1,8,6,2,5,4,8,3,7});

        Assertions.assertEquals(49, area);
    }

    @Test
    void maxAreaWithoutDuplicates() {
        ContainerWithWater containerWithWater = new ContainerWithWater();

        int area = containerWithWater.maxArea(new int[]{3,6,1});

        Assertions.assertEquals(3, area);
    }
}