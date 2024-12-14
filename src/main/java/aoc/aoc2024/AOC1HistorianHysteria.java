package aoc.aoc2024;

import aoc.AOCTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AOC1HistorianHysteria extends AOCTask<AOC1HistorianHysteria.AOC1InputData> {

    @Override
    public String getTaskTitle() {
        return "Day 1: Historian Hysteria";
    }

    @Override
    public AOCAnswer solve(AOC1InputData inputData) {
        List<Integer> first = inputData.first();
        List<Integer> second = inputData.second();

        Collections.sort(first);
        Collections.sort(second);

        int result1 = 0;

        for (int i = 0; i < first.size(); i++) {
            result1 += Math.abs(first.get(i) - second.get(i));
        }

        Map<Integer, Integer> frequency = HashMap.newHashMap(first.size());

        for (Integer i : second) {
            frequency.compute(i, (key, value) -> value == null ? 1 : value + 1);
        }

        int result2 = 0;

        for (Integer i : first) {
            result2 += frequency.getOrDefault(i, 0) * i;
        }

        return new AOCAnswer(result1, result2);
    }

    @Override
    public AOC1InputData parseInputData(String fileContent) {

        List<Integer> integers = Arrays.stream(fileContent.split("\n| +")).map(Integer::valueOf).toList();

        List<Integer> first = new ArrayList<>(integers.size() / 2);
        List<Integer> second = new ArrayList<>(integers.size() / 2);

        for (int i = 0; i < integers.size(); i++) {
            if (i % 2 == 0) {
                first.add(integers.get(i));
            } else {
                second.add(integers.get(i));
            }
        }

        return new AOC1InputData(first, second);
    }

    public record AOC1InputData(List<Integer> first, List<Integer> second) implements AOCInputData {
    }
}
