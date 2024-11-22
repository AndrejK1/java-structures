package learning.examples.rumi;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static learning.examples.rumi.Constants.INITIAL_PLAYER_TILES_COUNT;

@Data
@Slf4j
public class RumiGame {
    private final List<Player> players;
    private List<RumiTileGroup> boardTileGroups;
    private List<RumiTile> rumiTilesInBox;
    private int currentPlayerIndex;
    private int turn;

    public RumiGame(int playersCount) {
        rumiTilesInBox = new ArrayList<>(Constants.ALL_RUMI_TILES);
        Collections.shuffle(rumiTilesInBox);
        boardTileGroups = new ArrayList<>();

        List<Player> newPlayers = new ArrayList<>();
        for (int i = 0; i < playersCount; i++) {
            newPlayers.add(new Player());
        }
        players = Collections.unmodifiableList(newPlayers);
    }

    public void play() {
        for (Player player : players) {
            for (int i = 0; i < INITIAL_PLAYER_TILES_COUNT; i++) {
                player.getRumiTiles().add(rumiTilesInBox.removeLast());
            }
        }

        Player currentPlayer = players.get(currentPlayerIndex);

        while (true) {
            performTurn(currentPlayer);
            turn++;

            if (currentPlayer.getRumiTiles().isEmpty() || turn > 1000) {
                break;
            }

            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        }

        log.info("Game finished");
    }

    private void performTurn(Player currentPlayer) {
        if (currentPlayer.isMadeFirstTurn()) {

        }
    }

    @Data
    public static class Player {
        private boolean madeFirstTurn;
        private List<RumiTile> rumiTiles = new ArrayList<>();
    }
}
