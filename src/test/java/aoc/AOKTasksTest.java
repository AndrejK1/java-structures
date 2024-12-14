package aoc;

import aoc.aoc2024.AOC1HistorianHysteria;
import aoc.aoc2024.AOC2RedNosedReports;
import aoc.aoc2024.AOC3MullItOver;
import aoc.aoc2024.AOC4CeresSearch;
import aoc.aoc2024.AOC5PrintQueue;
import aoc.aoc2024.AOC6GuardGallivant;
import aoc.aoc2024.AOC7BridgeRepair;
import aoc.aoc2024.AOC8ResonantCollinearity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AOKTasksTest {

    @Test
    void testDay1Task() {
        AOCTask.AOCAnswer aocAnswer = AOCTaskRunner.builder()
                .aokTask(new AOC1HistorianHysteria())
                .inputDataFile("aoc/2024/aoc1.txt")
                .build()
                .solveTask();

        Assertions.assertEquals(1646452L, aocAnswer.answerPart1());
        Assertions.assertEquals(23609874L, aocAnswer.answerPart2());
    }

    @Test
    void testDay2Task() {
        AOCTask.AOCAnswer aocAnswer = AOCTaskRunner.builder()
                .aokTask(new AOC2RedNosedReports())
                .inputDataFile("aoc/2024/aoc2.txt")
                .build()
                .solveTask();

        Assertions.assertEquals(332L, aocAnswer.answerPart1());
        Assertions.assertEquals(398L, aocAnswer.answerPart2());
    }

    @Test
    void testDay3Task() {
        AOCTask.AOCAnswer aocAnswer = AOCTaskRunner.builder()
                .aokTask(new AOC3MullItOver())
                .inputDataFile("aoc/2024/aoc3.txt")
                .build()
                .solveTask();

        Assertions.assertEquals(174561379L, aocAnswer.answerPart1());
        Assertions.assertEquals(106921067L, aocAnswer.answerPart2());
    }

    @Test
    void testDay4Task() {
        AOCTask.AOCAnswer aocAnswer = AOCTaskRunner.builder()
                .aokTask(new AOC4CeresSearch())
                .inputDataFile("aoc/2024/aoc4.txt")
                .build()
                .solveTask();

        Assertions.assertEquals(2493L, aocAnswer.answerPart1());
        Assertions.assertEquals(1890L, aocAnswer.answerPart2());
    }

    @Test
    void testDay5Task() {
        AOCTask.AOCAnswer aocAnswer = AOCTaskRunner.builder()
                .aokTask(new AOC5PrintQueue())
                .inputDataFile("aoc/2024/aoc5.txt")
                .build()
                .solveTask();

        Assertions.assertEquals(6260L, aocAnswer.answerPart1());
        Assertions.assertEquals(5346L, aocAnswer.answerPart2());
    }

    @Test
    void testDay6Task() {
        AOCTask.AOCAnswer aocAnswer = AOCTaskRunner.builder()
                .aokTask(new AOC6GuardGallivant())
                .inputDataFile("aoc/2024/aoc6.txt")
                .build()
                .solveTask();

        Assertions.assertEquals(4789L, aocAnswer.answerPart1());
        Assertions.assertEquals(1304L, aocAnswer.answerPart2());
    }

    @Test
    void testDay7Task() {
        AOCTask.AOCAnswer aocAnswer = AOCTaskRunner.builder()
                .aokTask(new AOC7BridgeRepair())
                .inputDataFile("aoc/2024/aoc7.txt")
                .build()
                .solveTask();

        Assertions.assertEquals(1260333054159L, aocAnswer.answerPart1());
        Assertions.assertEquals(162042343638683L, aocAnswer.answerPart2());
    }

    @Test
    void testDay8Task() {
        AOCTask.AOCAnswer aocAnswer = AOCTaskRunner.builder()
                .aokTask(new AOC8ResonantCollinearity())
                .inputDataFile("aoc/2024/aoc8.txt")
                .build()
                .solveTask();

        Assertions.assertEquals(222L, aocAnswer.answerPart1());
        Assertions.assertEquals(884L, aocAnswer.answerPart2());
    }

}
