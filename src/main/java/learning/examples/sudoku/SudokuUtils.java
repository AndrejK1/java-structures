package learning.examples.sudoku;

import additional.Pair;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@UtilityClass
public class SudokuUtils {
    public static List<Integer> generateListWithNumbersTill(int endInclusive) {
        List<Integer> list = new ArrayList<>();

        for (int i = 1; i <= endInclusive; i++) {
            list.add(i);
        }

        return list;
    }

    public static Map<Integer, List<Integer>> findAllPossiblePositionsForUnsolvedNumbers(Map<Integer, List<Integer>> possibleNumbersByPos) {
        Map<Integer, List<Integer>> numbersAndPositions = new HashMap<>();
        List<Integer> exclusions = new ArrayList<>();

        for (Map.Entry<Integer, List<Integer>> entry : possibleNumbersByPos.entrySet()) {
            if (entry.getValue().size() == 1) {
                exclusions.add(entry.getValue().getFirst());
                continue;
            }

            entry.getValue().forEach(value -> {
                numbersAndPositions.putIfAbsent(value, new ArrayList<>());
                numbersAndPositions.get(value).add(entry.getKey());
            });
        }

        exclusions.forEach(numbersAndPositions::remove);
        return numbersAndPositions;
    }

    public static String buildLog(SudokuSolver sudokuHolder, String alg, Integer pos, Object stateNew) {
        Pair<Integer, Integer> positionCoordinates = sudokuHolder.getSudoku().getPositionCoordinates(pos);
        return alg +
                ": pos " + String.format("%1$2s", pos) + " (" + positionCoordinates.getKey() + "," + positionCoordinates.getValue() + ")" +
                " | state: " + sudokuHolder.getPossibleNumbersByPosition(pos) +
                " | update: " + stateNew;
    }

    public static boolean haveIntersection(List<Integer> left, List<Integer> right) {
        HashSet<Integer> set = new HashSet<>(left);
        return right.stream().anyMatch(set::contains);
    }
}
