package learning.examples.rumi;

import lombok.Data;

import static learning.examples.rumi.Constants.JOKER_WEIGHT;
import static learning.examples.rumi.Constants.MIN_WEIGHT;

@Data
public class RumiTile {
    public RumiTile(Color color, int weight) {
        if (color == null) {
            throw new IllegalArgumentException("Color can't be mull");
        }

        if (weight < MIN_WEIGHT || weight > Constants.MAX_WEIGHT) {
            throw new IllegalArgumentException("incorrect weight " + weight);
        }

        this.color = color;
        this.weight = weight;
    }

    private final Color color;
    private final int weight;

    public boolean isJoker() {
        return weight == JOKER_WEIGHT;
    }

    public enum Color {
        BLACK, BLUE, RED, ORANGE;
    }
}
