package aoc.aoc2024;

import aoc.AOCTask;

import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class AOC11PlutonianPebbles implements AOCTask<AOC11PlutonianPebbles.AOC11InputData> {

    @Override
    public String getTaskTitle() {
        return "Day 11: Plutonian Pebbles";
    }

    @Override
    public AOCAnswer solve(AOC11InputData inputData) {
        int result1 = blink(new LinkedList<>(inputData.stones()), 25).size();
        int result2 = blink(new LinkedList<>(inputData.stones()), 75).size();

        return new AOCAnswer(result1, result2);
    }

    private List<Long> blink(LinkedList<Long> stones, int times) {
        for (int i = 0; i < times; i++) {
            long startTime = System.currentTimeMillis();

            ListIterator<Long> listIterator = stones.listIterator();

            while (listIterator.hasNext()) {
                Long stone = listIterator.next();

                if (stone == 0L) {
                    listIterator.set(1L);
                } else if (((int) Math.log10(stone) + 1) % 2 == 0) {
                    int digitsCount = (int) Math.log10(stone) + 1;
                    int divider = (int) Math.pow(10, digitsCount / 2);

                    listIterator.set(stone / divider);
                    listIterator.add(stone % divider);
                } else {
                    listIterator.set(stone * 2024);
                }
            }

            logEnd(i, startTime, stones);
        }

        return stones;
    }

    private void logEnd(int i, long startTime, List<Long> stones) {
        System.out.println("Iteration " + (1 + i) + ": " + (System.currentTimeMillis() - (double) startTime) / 1000 + "s - " + stones.size() + " el");
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
