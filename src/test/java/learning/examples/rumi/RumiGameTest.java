package learning.examples.rumi;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RumiGameTest {

    @Test
    void play() {
        RumiGame rumiGame = new RumiGame(4);
        rumiGame.play();
    }
}