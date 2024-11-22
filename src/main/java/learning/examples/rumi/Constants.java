package learning.examples.rumi;


import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.List;

@UtilityClass
public class Constants {
    public static final int MAX_WEIGHT = 13;
    public static final int MIN_WEIGHT = 0;
    public static final int JOKER_WEIGHT = 0;
    public static final int INITIAL_PLAYER_TILES_COUNT = 14;
    public static final int COLORS_COUNT = RumiTile.Color.values().length;
    public static final List<RumiTile> ALL_RUMI_TILES = Collections.unmodifiableList(Utils.generateFullTileSet());
}
