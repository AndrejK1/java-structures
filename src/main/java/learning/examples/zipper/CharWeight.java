package learning.examples.zipper;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CharWeight implements Comparable<CharWeight> {
    private Integer weight;
    private Character character;

    @Override
    public int compareTo(CharWeight charWeight) {
        return weight.compareTo(charWeight.weight);
    }
}
