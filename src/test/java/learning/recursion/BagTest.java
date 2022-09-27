package learning.recursion;

import learning.recursion.bag.Bag;
import learning.recursion.bag.BagResults;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Slf4j
public class BagTest {

    @Test
    public void testBag() {
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

        Assert.assertEquals(canBeSolved, bagResults.isSolved());
        Assert.assertEquals(canBeSolved, bagResults.getWeight() == size);

        if (!canBeSolved) {
            Assert.assertTrue(bagResults.getThings().containsAll(thingsToFit));
        }
    }
}
