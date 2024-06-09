package learning.examples.recursion;

import learning.examples.recursion.hanoi.HanoiCallbackSummary;
import learning.examples.recursion.hanoi.HanoiTower;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HanoiTowerTest {

    @Test
    void test() {
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

        Assertions.assertEquals(calcTurns(size), summary.getTurns());
        Assertions.assertEquals(calcTurns(size), summary.getEstimatedTurns());
        Assertions.assertEquals(size, summary.getHeight());
        Assertions.assertEquals(0, summary.getA().size());
        Assertions.assertEquals(0, summary.getB().size());
        Assertions.assertEquals(size, summary.getC().size());
        Assertions.assertTrue(summary.isSolved());
    }

    private int calcTurns(int size) {
        if (size == 1) {
            return 1;
        }

        return 1 + 2 * calcTurns(size - 1);
    }
}
