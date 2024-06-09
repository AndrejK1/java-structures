package learning.examples.recursion;

import learning.examples.recursion.bag.Bag;
import learning.examples.recursion.bag.BagResults;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
class BagTest {

    @Test
    void testBag() {
        innerBagTest(true, 10, 2, 3, 4, 5, 1);
        innerBagTest(false, 13, 15, 6);
        innerBagTest(false, 1, 3, 4, 5);

        innerBagTest(true, 120, 6, 7, 50, 74, 64, 34, 53, 1);
        innerBagTest(false, 120, 6, 7, 51, 74, 64, 34, 53, 1);
        innerBagTest(true, 45, 34, 12, 1, 10);
    }

    private void innerBagTest(boolean canBeSolved, int size, Integer... integers) {
        Bag bag = new Bag(size);
        ArrayList<Integer> thingsToFit = new ArrayList<>(Arrays.asList(integers));
        BagResults bagResults = bag.tryToFitThings(thingsToFit);

        log.info(bagResults.toString());

        Assertions.assertEquals(canBeSolved, bagResults.isSolved());
        Assertions.assertEquals(canBeSolved, bagResults.getWeight() == size);

        if (!canBeSolved) {
            Assertions.assertTrue(bagResults.getThings().containsAll(thingsToFit));
        }
    }
}
