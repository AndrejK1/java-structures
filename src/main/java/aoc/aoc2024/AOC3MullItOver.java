package aoc.aoc2024;

import additional.Pair;
import aoc.AOCTask;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class AOC3MullItOver extends AOCTask<AOC3MullItOver.AOC3InputData> {

    @Override
    public String getTaskTitle() {
        return "Day 3: Mull It Over";
    }

    @Override
    public AOCAnswer solve(AOC3InputData inputData) {
        int result1 = runMultiplicationOnData(inputData.allMulCalls());
        int result2 = runMultiplicationOnData(inputData.enabledMulCalls());

        return new AOCAnswer(result1, result2);
    }

    private static int runMultiplicationOnData(List<Pair<Integer, Integer>> mulCalls) {
        int result = 0;

        for (Pair<Integer, Integer> pair : mulCalls) {
            result += pair.getKey() * pair.getValue();
        }

        return result;
    }

    @Override
    public AOC3InputData parseInputData(String fileContent) {
        Pattern functionPattern = Pattern.compile("mul\\(\\d+,\\d+\\)|do\\(\\)|don't\\(\\)");

        Matcher matcher = functionPattern.matcher(fileContent);

        List<Pair<Integer, Integer>> enabledMulCalls = new ArrayList<>();
        List<Pair<Integer, Integer>> allMulCalls = new ArrayList<>();

        boolean instructionsEnabled = true;

        while (matcher.find()) {
            String functionCall = matcher.group();

            if (functionCall.contains("don't")) {
                instructionsEnabled = false;
            } else if (functionCall.contains("do")) {
                instructionsEnabled = true;
            } else {
                String[] numbers = functionCall.replace("mul(", "").replace(")", "").split(",");
                allMulCalls.add(Pair.of(Integer.valueOf(numbers[0]), Integer.valueOf(numbers[1])));

                if (instructionsEnabled) {
                    enabledMulCalls.add(Pair.of(Integer.valueOf(numbers[0]), Integer.valueOf(numbers[1])));
                }
            }
        }

        return new AOC3InputData(allMulCalls, enabledMulCalls);
    }

    public record AOC3InputData(List<Pair<Integer, Integer>> allMulCalls,
                                List<Pair<Integer, Integer>> enabledMulCalls) implements AOCInputData {
    }
}
