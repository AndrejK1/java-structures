package aoc.aoc2024;

import aoc.AOCTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AOC11PlutonianPebbles implements AOCTask<AOC11PlutonianPebbles.AOC11InputData> {

    @Override
    public String getTaskTitle() {
        return "Day 11: Plutonian Pebbles";
    }

    @Override
    public AOCAnswer solve(AOC11InputData inputData) {
        int result1 = blink(new ArrayList<>(inputData.stones()), 25).size();
        int result2 = blink(new ArrayList<>(inputData.stones()), 75).size();

        return new AOCAnswer(result1, result2);
    }

    private List<Long> blink(List<Long> stones, int times) {
        for (int i = 0; i < times; i++) {
            for (int j = 0; j < stones.size(); j++) {
                Long stone = stones.get(j);

                if (stone == 0L) {
                    stones.set(j, 1L);
                } else if (((int) Math.log10(stone) + 1) % 2 == 0) {
                    int digitsCount = (int) Math.log10(stone) + 1;
                    int divider = (int) Math.pow(10, digitsCount / 2);

                    stones.set(j, stone / divider);
                    stones.add(j + 1, stone % divider);
                    j++;
                } else {
                    stones.set(j, stone * 2024);
                }
            }
        }

        return stones;
    }

    @Override
    public AOC11InputData parseInputData(String fileContent) {
        return new AOC11InputData(
                Arrays.stream(fileContent.split(" ")).map(Long::parseLong).toList()
        );
    }

    public record AOC11InputData(List<Long> stones) implements AOCInputData {
    }
}
