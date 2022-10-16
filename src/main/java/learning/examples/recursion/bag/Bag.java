package learning.examples.recursion.bag;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class Bag {
    private final int size;

    public BagResults tryToFitThings(List<Integer> thingsToFit) {
        List<Integer> things = new ArrayList<>(thingsToFit);

        int fullWeight = calcWeight(things);

        if (fullWeight == size) {
            return BagResults.solved(things);
        }

        if (fullWeight < size) {
            return BagResults.unsolved(size, things);
        }

        List<Integer> resultThings = tryToFit(things);

        if (calcWeight(resultThings) == size) {
            return BagResults.solved(resultThings);
        }

        return BagResults.unsolved(size, resultThings);
    }

    private List<Integer> tryToFit(List<Integer> thingsToFit) {
        if (thingsToFit.size() == 1) {
            return thingsToFit;
        }

        for (int i = 0; i < thingsToFit.size(); i++) {
            Integer first = thingsToFit.get(0);

            List<Integer> innerList = tryToFit(new ArrayList<>(
                    thingsToFit.subList(1, thingsToFit.size())));

            if (calcWeight(innerList) == size) {
                return innerList;
            }

            innerList.add(0, first);

            if (calcWeight(innerList) == size) {
                return innerList;
            }

            shiftList(thingsToFit);
        }

        return thingsToFit;
    }

    private void shiftList(List<Integer> things) {
        if (things.size() > 1) {
            things.add(things.remove(0));
        }
    }

    private Integer calcWeight(List<Integer> resultThings) {
        return resultThings.stream().reduce(0, Integer::sum);
    }
}
