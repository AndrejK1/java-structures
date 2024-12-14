package aoc.aoc2024;

import aoc.AOCTask;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class AOC5PrintQueue implements AOCTask<AOC5PrintQueue.AOC5InputData> {

    @Override
    public String getTaskTitle() {
        return "Day 5: Print Queue";
    }

    @Override
    public AOCAnswer solve(AOC5InputData inputData) {
        int[][] rules = inputData.pageRules();
        List<List<Integer>> updates = inputData.pageUpdates();

        Map<Integer, Set<Integer>> rulesByNumber = new HashMap<>();

        for (int[] rule : rules) {
            rulesByNumber.compute(rule[0], (key, value) -> {
                if (value == null) {
                    value = new HashSet<>();
                }

                value.add(rule[1]);

                return value;
            });
        }

        int correctlyOrderedUpdatesMiddleSum = 0;
        int fixedOrderedUpdatesMiddleSum = 0;

        outer:
        for (List<Integer> update : updates) {
            for (int i = 0; i < update.size() - 1; i++) {
                Set<Integer> secondNums = rulesByNumber.get(update.get(i));

                if (secondNums == null || !secondNums.contains(update.get(i + 1))) {
                    List<Integer> fixedUpdate = fixUpdate(update, rulesByNumber);

                    fixedOrderedUpdatesMiddleSum += fixedUpdate.get(fixedUpdate.size() / 2);
                    continue outer;
                }
            }

            correctlyOrderedUpdatesMiddleSum += update.get(update.size() / 2);
        }

        return new AOCAnswer(correctlyOrderedUpdatesMiddleSum, fixedOrderedUpdatesMiddleSum);
    }

    private List<Integer> fixUpdate(List<Integer> update, Map<Integer, Set<Integer>> rulesByNumber) {
        update.sort((num, num2) -> {
            if (rulesByNumber.get(num).contains(num2)) {
                return -1;
            }

            if (rulesByNumber.get(num2).contains(num)) {
                return 1;
            }

            return 0;
        });
        return update;
    }

    @Override
    public AOC5InputData parseInputData(String fileContent) {
        String[] dataParts = fileContent.split("\n\n");
        String[] pageRulesStrArray = dataParts[0].split("\n");
        String[] pageUpdatesStrArray = dataParts[1].split("\n");

        int[][] pageRules = new int[pageRulesStrArray.length][2];

        for (int i = 0; i < pageRulesStrArray.length; i++) {
            String[] pageRuleNumbers = pageRulesStrArray[i].split("\\|");

            pageRules[i][0] = Integer.parseInt(pageRuleNumbers[0]);
            pageRules[i][1] = Integer.parseInt(pageRuleNumbers[1]);
        }

        List<List<Integer>> pageUpdates = new ArrayList<>(pageUpdatesStrArray.length);

        for (String s : pageUpdatesStrArray) {
            pageUpdates.add(Arrays.stream(s.split(","))
                    .map(Integer::valueOf)
                    .collect(Collectors.toList()));
        }

        return new AOC5InputData(pageRules, pageUpdates);
    }

    public record AOC5InputData(int[][] pageRules, List<List<Integer>> pageUpdates) implements AOCInputData {
    }
}
