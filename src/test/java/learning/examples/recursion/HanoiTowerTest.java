package learning.examples.recursion;

import learning.examples.recursion.hanoi.HanoiCallbackSummary;
import learning.examples.recursion.hanoi.HanoiTower;
import org.junit.Assert;
import org.junit.Test;

public class HanoiTowerTest {

    @Test
    public void test() {
        testHanoi(1);
        System.out.println();
        testHanoi(2);
        System.out.println();
        testHanoi(4);
        System.out.println();
        testHanoi(10);
    }

    private void testHanoi(int size) {
        HanoiTower hanoiTower = new HanoiTower(size);

        HanoiCallbackSummary summary = hanoiTower.solve(s -> System.out.println(s.currentTurn()));

        Assert.assertEquals(calcTurns(size), summary.getTurns());
        Assert.assertEquals(calcTurns(size), summary.getEstimatedTurns());
        Assert.assertEquals(size, summary.getHeight());
        Assert.assertEquals(0, summary.getA().size());
        Assert.assertEquals(0, summary.getB().size());
        Assert.assertEquals(size, summary.getC().size());
        Assert.assertTrue(summary.isSolved());
    }

    private int calcTurns(int size) {
        if (size == 1) {
            return 1;
        }

        return 1 + 2 * calcTurns(size - 1);
    }
}
