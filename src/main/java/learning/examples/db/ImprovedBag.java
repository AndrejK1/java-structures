package learning.examples.db;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImprovedBag {

    public static long findSolutionCount(int bagSize, List<Integer> thingWeights) {
        Map<Integer, Long> scoreMemo = new HashMap<>();
        scoreMemo.put(0, 1L);

        for (int i = 1; i < bagSize + 1; i++) {
            long currentMemo = 0;

            for (Integer thingWeight : thingWeights) {
                int subBagSize = i - thingWeight;

                if (scoreMemo.containsKey(subBagSize)) {
                    currentMemo += scoreMemo.get(subBagSize);
                }
            }

            scoreMemo.put(i, currentMemo);
        }

        return scoreMemo.get(bagSize);
    }


    public static int findMinimalThingsCount(int bagSize, List<Integer> thingWeights) {
        Map<Integer, Integer> scoreMemo = new HashMap<>();
        scoreMemo.put(0, 0);

        for (int i = 1; i < bagSize + 1; i++) {
            for (Integer thingWeight : thingWeights) {
                int subBagSize = i - thingWeight;

                if (subBagSize < 0) {
                    continue;
                }

                Integer subBagMemo = Optional.ofNullable(scoreMemo.get(subBagSize)).map(sbr -> sbr + 1).orElse(null);
                Integer currentMemo = scoreMemo.get(i);

                if (isFirstNullOrBigger(currentMemo, subBagMemo) && subBagMemo != null) {
                    scoreMemo.put(i, subBagMemo);
                }
            }
        }

        return scoreMemo.get(bagSize);
    }

    public static BagInfo findMinimalThingsCountWithSolution(int bagSize, List<Integer> thingWeights) {
        Map<Integer, BagInfo> scoreMemo = new HashMap<>();
        scoreMemo.put(0, new BagInfo(Collections.emptyList()));

        for (int i = 1; i < bagSize + 1; i++) {
            for (Integer thingWeight : thingWeights) {
                int subBagSize = i - thingWeight;

                if (subBagSize < 0) {
                    continue;
                }

                BagInfo subBagMemo = scoreMemo.get(subBagSize);

                Integer subBagResult = Optional.ofNullable(subBagMemo).map(BagInfo::getThingsCount).map(sbr -> sbr + 1).orElse(null);
                Integer currentResult = Optional.ofNullable(scoreMemo.get(i)).map(BagInfo::getThingsCount).orElse(null);

                if (isFirstNullOrBigger(currentResult, subBagResult) && subBagResult != null) {
                    List<Integer> things = new ArrayList<>(subBagMemo.getThings());
                    things.add(thingWeight);

                    scoreMemo.put(i, new BagInfo(things));
                }
            }
        }

        return scoreMemo.get(bagSize);
    }

    private static boolean isFirstNullOrBigger(Integer first, Integer second) {
        if (first == null) {
            return true;
        }

        if (second == null) {
            return false;
        }

        return first > second;
    }

    @Getter
    @RequiredArgsConstructor
    public static class BagInfo {
        private final List<Integer> things;

        public int getThingsCount() {
            return things.size();
        }

        public int getThingsSum() {
            return things.stream().reduce(0, Integer::sum);
        }
    }

    @Getter
    public static class BagResults {
        private final boolean solved;
        private final List<Integer> things;
        private final int size;
        private final int solutions;
        private final int weight;

        private BagResults(List<Integer> things, int size, int solutions) {
            this.things = things;
            this.size = size;
            this.solutions = solutions;
            this.weight = things.stream().reduce(0, Integer::sum);
            this.solved = size == weight;
        }

        @Override
        public String toString() {
            return "Size " + size + " bag " +
                    (solved ? "was " : "can't be ") +
                    "filled with things " + things;
        }
    }

}
