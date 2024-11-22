package learning.examples.rumi;


import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class Utils {
    public static List<RumiTile> generateFullTileSet() {
        List<RumiTile> rumiTiles = new ArrayList<>();

        for (int weight = 1; weight <= Constants.MAX_WEIGHT; weight++) {
            for (RumiTile.Color color : RumiTile.Color.values()) {
                rumiTiles.add(new RumiTile(color, weight));
                rumiTiles.add(new RumiTile(color, weight));
            }
        }

        rumiTiles.add(new RumiTile(RumiTile.Color.BLACK, 0));
        rumiTiles.add(new RumiTile(RumiTile.Color.RED, 0));

        return rumiTiles;
    }
}
