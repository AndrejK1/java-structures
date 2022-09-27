package learning.recursion.bag;

import lombok.Getter;

import java.util.List;

@Getter
public class BagResults {
    private final boolean solved;
    private final List<Integer> things;
    private final int size;
    private final int weight;

    private BagResults(List<Integer> things) {
        this(things, things.stream().reduce(0, Integer::sum));
    }

    private BagResults(List<Integer> things, int size) {
        this.things = things;
        this.size = size;
        this.weight = things.stream().reduce(0, Integer::sum);
        this.solved = size == weight;
    }

    public static BagResults solved(List<Integer> things) {
        return new BagResults(things);
    }

    public static BagResults unsolved(int size, List<Integer> things) {
        return new BagResults(things, size);
    }

    @Override
    public String toString() {
        return "Size " + size + " bag " +
                (solved ? "was " : "can't be ") +
                "filled with things " + things;
    }
}
