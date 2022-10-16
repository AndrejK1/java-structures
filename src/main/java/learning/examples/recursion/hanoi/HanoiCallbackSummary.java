package learning.examples.recursion.hanoi;

import learning.list.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HanoiCallbackSummary {
    private boolean solved;
    private long height;
    private long turns;
    private long estimatedTurns;
    private List<Integer> a;
    private List<Integer> b;
    private List<Integer> c;

    private int currentDisk;
    private String from;
    private String to;

    public String currentTurn() {
        return solved ? "FINISHED in " + turns + " turns!" : String.format("%-6d %s: %s -> %s", turns, currentDisk, from, to);
    }
}
