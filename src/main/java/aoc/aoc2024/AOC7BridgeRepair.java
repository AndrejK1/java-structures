package aoc.aoc2024;

import additional.Pair;
import aoc.AOCTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;

public class AOC7BridgeRepair implements AOCTask<AOC7BridgeRepair.AOC7InputData> {

    private static final Map<String, BinaryOperator<Long>> OPERATORS_V1 = Map.of(
            "+", Long::sum,
            "*", (l, l2) -> l * l2
    );

    private static final Map<String, BinaryOperator<Long>> OPERATORS_V2 = Map.of(
            "+", Long::sum,
            "*", (l, l2) -> l * l2,
            "|", (l, l2) -> l * (long) Math.pow(10, String.valueOf(l2).length()) + l2
    );

    @Override
    public String getTaskTitle() {
        return "Day 7: Bridge Repair";
    }

    @Override
    public AOCAnswer solve(AOC7InputData inputData) {
        long possibleEquations = 0;
        long possibleEquationsV2 = 0;

        for (Pair<Long, List<Long>> equation : inputData.equations()) {
            if (checkEquationSolutionExistence(equation.getKey(), equation.getValue(), OPERATORS_V1)) {
                possibleEquations += equation.getKey();
            }
        }

        for (Pair<Long, List<Long>> equation : inputData.equations()) {
            if (checkEquationSolutionExistence(equation.getKey(), equation.getValue(), OPERATORS_V2)) {
                possibleEquationsV2 += equation.getKey();
            }
        }

        return new AOCAnswer(possibleEquations, possibleEquationsV2);
    }

    private boolean checkEquationSolutionExistence(long equationResult, List<Long> equationOperands,
                                                   Map<String, BinaryOperator<Long>> operators) {
        return checkEquationSolutionExistenceBackTrack(equationResult, equationOperands, operators, 0, 0);
    }

    private boolean checkEquationSolutionExistenceBackTrack(long equationResult, List<Long> equationOperands,
                                                            Map<String, BinaryOperator<Long>> operators,
                                                            int idx, long currentSum) {
        if (currentSum == equationResult && idx == equationOperands.size()) {
            return true;
        }

        if (currentSum > equationResult || idx >= equationOperands.size()) {
            return false;
        }

        boolean foundSolution = false;

        for (Map.Entry<String, BinaryOperator<Long>> operator : operators.entrySet()) {
            if (!foundSolution && !(operator.getKey().equals("*") && idx == 0)) {
                foundSolution = checkEquationSolutionExistenceBackTrack(equationResult, equationOperands, operators, idx + 1, operator.getValue().apply(currentSum, equationOperands.get(idx)));
            }
        }

        return foundSolution;
    }

    @Override
    public AOC7InputData parseInputData(String fileContent) {
        List<Pair<Long, List<Long>>> equations = new ArrayList<>();

        for (String equation : fileContent.split("\n")) {
            String[] equationParts = equation.split(": ");

            long equationResult = Long.parseLong(equationParts[0]);
            List<Long> equationOperands = Arrays.stream(equationParts[1].split(" ")).map(Long::parseLong).toList();
            equations.add(Pair.of(equationResult, equationOperands));
        }

        return new AOC7InputData(equations);
    }

    public record AOC7InputData(List<Pair<Long, List<Long>>> equations) implements AOCInputData {
    }
}
