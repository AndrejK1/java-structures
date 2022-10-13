package learning.recursion.hanoi;

import learning.list.SimpleLinkedList;
import learning.list.MutableList;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class HanoiTower {
    private static final String A = "a";
    private static final String B = "b";
    private static final String C = "c";
    private final Map<String, MutableList<Integer>> towers = new HashMap<>();
    private long moves;
    private long estimatedTurns;
    private int height;

    public HanoiTower(int height) {
        init(height);
    }

    public HanoiCallbackSummary solve() {
        return solve(s -> {});
    }

    public HanoiCallbackSummary solve(Consumer<HanoiCallbackSummary> turnCallback) {
        solveTower(towers.get(A).size(), A, B, C, turnCallback);
        HanoiCallbackSummary finalState = returnFinalState();
        init(height);
        return finalState;
    }

    private HanoiCallbackSummary returnFinalState() {
        return new HanoiCallbackSummary(true, height, moves, estimatedTurns,
                towers.get(A), towers.get(B), towers.get(C), 0, null, null);
    }

    private void solveTower(int size, String from, String intermediate, String to, Consumer<HanoiCallbackSummary> turnCallback) {
        if (size == 1) {
            moveTop(from, to, turnCallback);
            return;
        }

        solveTower(size - 1, from, to, intermediate, turnCallback);
        moveTop(from, to, turnCallback);
        solveTower(size - 1, intermediate, from, to, turnCallback);
    }

    private void moveTop(String from, String to, Consumer<HanoiCallbackSummary> turnCallback) {
        Integer disk = towers.get(from).remove(0);
        towers.get(to).add(disk, 0);
        turnCallback.accept(new HanoiCallbackSummary(false, height, ++moves, estimatedTurns,
                towers.get(A), towers.get(B), towers.get(C), disk, from, to));
    }

    private void init(int height) {
        if (height < 1) {
            throw new IllegalArgumentException("Disks count must be > 0");
        }

        moves = 0;
        this.height = height;
        this.estimatedTurns = Math.round(Math.pow(2, height)) - 1;

        towers.put(A, new SimpleLinkedList<>());
        towers.put(B, new SimpleLinkedList<>());
        towers.put(C, new SimpleLinkedList<>());

        IntStream.rangeClosed(1, height)
                .boxed()
                .forEach(i -> towers.get(A).add(i));
    }
}
