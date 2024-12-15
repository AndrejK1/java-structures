package aoc.aoc2024;

import aoc.AOCTask;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AOC11PlutonianPebbles implements AOCTask<AOC11PlutonianPebbles.AOC11InputData> {

    @Override
    public String getTaskTitle() {
        return "Day 11: Plutonian Pebbles";
    }

    @Override
    public AOCAnswer solve(AOC11InputData inputData) {
        long result1 = blink(inputData.stones(), 25);
        long result2 = blink(inputData.stones(), 75);

        return new AOCAnswer(result1, result2);
    }

    private long blink(List<Integer> stones, int times) {
        if (times <= 0) {
            return stones.size();
        }

        Map<String, Long> memo = new HashMap<>();

        long result = 0;

        for (Integer stone : stones) {
            result += calcStoneCount(stone, times - 1, memo);
        }

        return result;
    }

    private long calcStoneCount(long stone, int blinkLeft, Map<String, Long> memo) {
        String key = stone + "." + blinkLeft;

        if (memo.get(key) != null && memo.get(key) != 0) {
            return memo.get(key);
        }

        if (blinkLeft == 0) {
            memo.put(key, hasEvenDigitsCount(stone) ? 2L : 1L);
            return memo.get(key);
        }

        long result;

        if (stone == 0) {
            result = calcStoneCount(1, blinkLeft - 1, memo);
        } else if (hasEvenDigitsCount(stone)) {
            int divider = (int) Math.pow(10, getDigitsCount(stone) / 2);

            result = calcStoneCount(stone / divider, blinkLeft - 1, memo)
                    + calcStoneCount(stone % divider, blinkLeft - 1, memo);
        } else {
            result = calcStoneCount(stone * 2024, blinkLeft - 1, memo);
        }

        memo.put(key, result);

        return result;
    }

    private boolean hasEvenDigitsCount(long num) {
        return getDigitsCount(num) % 2 == 0;
    }

    private static int getDigitsCount(long num) {
        return (int) Math.log10(num) + 1;
    }

    @Override
    public AOC11InputData parseInputData(String fileContent) {
        return new AOC11InputData(
                Arrays.stream(fileContent.split(" ")).map(Integer::parseInt).toList()
        );
    }

    public record AOC11InputData(List<Integer> stones) implements AOCInputData {
    }
}
