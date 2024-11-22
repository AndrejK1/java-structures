package learning.examples.rumi;

import lombok.Data;

import java.util.Collection;
import java.util.Collections;
import java.util.IntSummaryStatistics;
import java.util.List;

@Data
public class RumiTileGroup {
    private final List<RumiTile> rumiTiles;
    private final Type type;

    public RumiTileGroup(List<RumiTile> rumiTiles) {
        if (rumiTiles == null || rumiTiles.isEmpty()) {
            throw new IllegalArgumentException("Attempt to create Empty Tile Group");
        }

        if (rumiTiles.size() == 1) {
            this.rumiTiles = rumiTiles;
            this.type = Type.SINGLE_UNDEFINED;
            return;
        }

        long distinctColors = countDistinctColors(rumiTiles);

        if (distinctColors != rumiTiles.size() && distinctColors != 1) {
            throw new IllegalArgumentException("Incorrect amount of different colors");
        }

        if (distinctColors == 1 && !hasConsecutiveWeights(rumiTiles)) {
            throw new IllegalArgumentException("One-color group should have consecutive numbers");
        }

        if (distinctColors == rumiTiles.size() && countDistinctWeights(rumiTiles) != 1) {
            throw new IllegalArgumentException("One-color group should have consecutive numbers");
        }

        this.rumiTiles = Collections.unmodifiableList(rumiTiles);
        this.type = distinctColors == 1 ? Type.CONSECUTIVE_WEIGHT : Type.COLOR_SET;
    }

    public boolean isFull() {
        return isSolved() && (
                (type == Type.COLOR_SET && rumiTiles.size() == Constants.COLORS_COUNT)
                        || (type == Type.CONSECUTIVE_WEIGHT && rumiTiles.size() == Constants.MAX_WEIGHT)
        );
    }

    public boolean isSolved() {
        return rumiTiles.size() >= 3 && type != Type.SINGLE_UNDEFINED;
    }

    private static long countDistinctColors(Collection<RumiTile> rumiTiles) {
        return rumiTiles.stream().map(RumiTile::getColor).distinct().count();
    }

    private static long countDistinctWeights(Collection<RumiTile> rumiTiles) {
        return rumiTiles.stream().map(RumiTile::getWeight).distinct().count();
    }

    private static boolean hasConsecutiveWeights(Collection<RumiTile> rumiTiles) {
        IntSummaryStatistics tilesSummary = rumiTiles.stream().mapToInt(RumiTile::getWeight).summaryStatistics();
        return tilesSummary.getMax() - tilesSummary.getMin() + 1 == rumiTiles.size();
    }

    public enum Type {
        COLOR_SET, CONSECUTIVE_WEIGHT, SINGLE_UNDEFINED
    }
}
