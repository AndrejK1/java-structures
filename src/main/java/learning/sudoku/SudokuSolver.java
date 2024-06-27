package learning.sudoku;

import additional.Pair;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SudokuSolver {
    private final int fieldSize;
    private final int smallSquaresSize;
    private final int smallSquaresInLine;
    private final List<List<Integer>> possibleNumbers;
    private final Map<Integer, Integer> solvedNumbersByPositions;
    private final Map<Integer, List<Integer>> rowsPositions;
    private final Map<Integer, List<Integer>> columnsPositions;
    private final Map<Integer, List<Integer>> squaresPositions;

    @Setter
    private SudokuPrinter sudokuPrinter;

    private boolean isDirty;
    private int solutionStep;
    private boolean detectedChange;

    public static SudokuSolver newSudokuSolverInstance(List<Integer> input) {
        return new SudokuSolver(input);
    }

    private SudokuSolver(List<Integer> input) {
        validate(input);
        this.fieldSize = getFieldSize(input.size());
        this.smallSquaresSize = (int) Math.sqrt(fieldSize);
        this.solvedNumbersByPositions = new HashMap<>();
        this.squaresPositions = new HashMap<>();
        this.rowsPositions = new HashMap<>();
        this.columnsPositions = new HashMap<>();
        this.possibleNumbers = new ArrayList<>(input.size());
        this.smallSquaresInLine = fieldSize / smallSquaresSize;
        init(input, fieldSize);
    }

    public Solution solveSudoku() {
        if (isDirty) {
            throw new IllegalStateException("Sudoku solver was already used!");
        }

        isDirty = true;

        printState();

        boolean solved;

        while (true) {
            detectedChange = false;

            runSimpleCheck();

            if (!detectedChange) {
                runDeepCheck();
            }

            solutionStep++;

            solved = isSolved();

            if (solved || !detectedChange) {
                break;
            }

            printState();
        }

        printState();

        return new Solution(solved, covertResult(), fieldSize, smallSquaresInLine);
    }

    private void printState() {
        if (sudokuPrinter != null) {
            sudokuPrinter.printState(solutionStep, solvedNumbersByPositions, possibleNumbers, fieldSize, isSolved());
        }
    }

    private boolean isSolved() {
        return solvedNumbersByPositions.size() == fieldSize * fieldSize;
    }

    private List<Integer> covertResult() {
        return possibleNumbers.stream()
                .map(list -> list.size() == 1 ? list.getFirst() : 0)
                .toList();
    }

    private void runSimpleCheck() {
        for (Map.Entry<Integer, Integer> entry : solvedNumbersByPositions.entrySet()) {
            Integer solvedPosition = entry.getKey();
            Integer solvedValue = entry.getValue();

            Pair<Integer, Integer> solvedValueCoords = getCoords(solvedPosition);

            // get all coords that are related to solved position and run number deletion from possible options
            detectedChange = Stream.of(
                            rowsPositions.get(solvedValueCoords.getKey()),
                            columnsPositions.get(solvedValueCoords.getValue()),
                            squaresPositions.get(getSquarePosFromPos(solvedPosition)))
                    .flatMap(List::stream)
                    .filter(positionToCheck -> !positionToCheck.equals(solvedPosition))
                    .map(possibleNumbers::get)
                    .map(numbers -> numbers.remove(solvedValue))
                    .reduce(detectedChange, (r1, r2) -> r1 || r2);
        }

        updateSolvedPositions();
    }

    private void updateSolvedPositions() {
        for (int position = 0; position < possibleNumbers.size(); position++) {
            List<Integer> possibleValuesAtPos = possibleNumbers.get(position);

            if (possibleValuesAtPos.isEmpty()) {
                throw new IllegalStateException("0 possible situations at position " + position);
            }

            if (possibleValuesAtPos.size() == 1) {
                solvedNumbersByPositions.putIfAbsent(position, possibleValuesAtPos.getFirst());
            }
        }
    }

    private void runDeepCheck() {
        for (List<Integer> squarePositions : rowsPositions.values()) {
            runDeepCheckAtPositions(squarePositions);
        }

        for (List<Integer> positions : columnsPositions.values()) {
            runDeepCheckAtPositions(positions);
        }

        for (List<Integer> positions : squaresPositions.values()) {
            runDeepCheckAtPositions(positions);
        }
    }

    private void runDeepCheckAtPositions(List<Integer> positionsToCheck) {
        Map<Integer, List<Integer>> possibleNumbersByPos = positionsToCheck.stream()
                .collect(Collectors.toMap(Function.identity(), possibleNumbers::get));

        checkForUniqueValueAcrossPossible(possibleNumbersByPos);
        updateSolvedPositions();

        checkForEqualGroups(possibleNumbersByPos);
        updateSolvedPositions();
    }

    private void checkForUniqueValueAcrossPossible(Map<Integer, List<Integer>> possibleNumbersByPos) {
        Map<Integer, List<Integer>> unsolvedNumbersWithPositions = findAllPossiblePositionsForUnsolvedNumbers(possibleNumbersByPos);

        for (Map.Entry<Integer, List<Integer>> numberWithPossiblePositions : unsolvedNumbersWithPositions.entrySet()) {
            Integer numberValue = numberWithPossiblePositions.getKey();
            List<Integer> numberPositions = numberWithPossiblePositions.getValue();

            if (numberPositions.size() == 1) {
                solvedNumbersByPositions.putIfAbsent(numberPositions.getFirst(), numberValue);
                possibleNumbers.set(numberPositions.getFirst(), new ArrayList<>(List.of(numberValue)));
                detectedChange = true;
                continue;
            }

            clearValueFromUniqueGroupsIfPossible(numberPositions, numberValue);
        }
    }

    private static Map<Integer, List<Integer>> findAllPossiblePositionsForUnsolvedNumbers(Map<Integer, List<Integer>> possibleNumbersByPos) {
        Map<Integer, List<Integer>> numbersAndPositions = new HashMap<>();
        List<Integer> exclusions = new ArrayList<>();

        for (Map.Entry<Integer, List<Integer>> entry : possibleNumbersByPos.entrySet()) {
            if (entry.getValue().size() <= 1) {
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

    private void clearValueFromUniqueGroupsIfPossible(List<Integer> numberPositions, Integer numberValue) {
        Place samePlacePositions = isSamePlacePositions(numberPositions);

        if (samePlacePositions.row != -1) {
            detectedChange = rowsPositions.get(samePlacePositions.row)
                    .stream()
                    .filter(pos -> !numberPositions.contains(pos))
                    .map(possibleNumbers::get)
                    .map(pos -> pos.remove(numberValue))
                    .reduce(detectedChange, (r1, r2) -> r1 || r2);
        }

        if (samePlacePositions.column != -1) {
            detectedChange = columnsPositions.get(samePlacePositions.column)
                    .stream()
                    .filter(pos -> !numberPositions.contains(pos))
                    .map(possibleNumbers::get)
                    .map(pos -> pos.remove(numberValue))
                    .reduce(detectedChange, (r1, r2) -> r1 || r2);

        }

        if (samePlacePositions.square != -1) {
            detectedChange = squaresPositions.get(samePlacePositions.square)
                    .stream()
                    .filter(pos -> !numberPositions.contains(pos))
                    .map(possibleNumbers::get)
                    .map(pos -> pos.remove(numberValue))
                    .reduce(detectedChange, (r1, r2) -> r1 || r2);

        }
    }

    private void checkForEqualGroups(Map<Integer, List<Integer>> possibleNumbersByPos) {
        Map<Integer, Map<Integer, List<Integer>>> positionGroupsBySizeToCheck = possibleNumbersByPos
                .entrySet()
                .stream()
                .filter(posList -> posList.getValue().size() > 1)
                .collect(Collectors.groupingBy(m -> m.getValue().size(), Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));


        for (Map.Entry<Integer, Map<Integer, List<Integer>>> positionGroups : positionGroupsBySizeToCheck.entrySet()) {
            Integer groupSize = positionGroups.getKey();
            Map<Integer, List<Integer>> groupByPosition = positionGroups.getValue();

            if (groupSize > groupByPosition.size()) {
                continue;
            }

            Map<List<Integer>, Map<Integer, List<Integer>>> equalSetsWithPositions = groupByPosition.entrySet()
                    .stream()
                    .collect(Collectors.groupingBy(Map.Entry::getValue, Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

            for (Map.Entry<List<Integer>, Map<Integer, List<Integer>>> equalSetsWithPosition : equalSetsWithPositions.entrySet()) {
                List<Integer> valuesSet = equalSetsWithPosition.getKey();
                Set<Integer> setPositions = equalSetsWithPosition.getValue().keySet();

                if (setPositions.size() < groupSize) {
                    continue;
                }

                for (Integer pos : possibleNumbersByPos.keySet()) {
                    if (!setPositions.contains(pos)) {
                        detectedChange = possibleNumbers.get(pos).removeAll(valuesSet) || detectedChange;
                    }
                }
            }
        }
    }

    private static void validate(List<Integer> input) {
        double fieldSize = Math.sqrt(input.size());

        if (((int) Math.round(Math.pow(fieldSize, 2))) != input.size()) {
            throw new IllegalArgumentException("Field is not square");
        }

        double smallSquaresSize = Math.sqrt((int) fieldSize);

        if (((int) fieldSize) % smallSquaresSize != 0) {
            throw new IllegalArgumentException("Can't fit squares");
        }
    }

    private static int getFieldSize(int size) {
        return (int) Math.sqrt(size);
    }

    private Place isSamePlacePositions(List<Integer> positions) {
        Set<Integer> rows = new HashSet<>();
        Set<Integer> cols = new HashSet<>();
        Set<Integer> sqs = new HashSet<>();

        positions.forEach(pos -> {
            Pair<Integer, Integer> coords = getCoords(pos);

            rows.add(coords.getKey());
            cols.add(coords.getValue());
            sqs.add(getSquarePosFromPos(pos));
        });

        return new Place(
                rows.size() == 1 ? rows.iterator().next() : -1,
                cols.size() == 1 ? cols.iterator().next() : -1,
                sqs.size() == 1 ? sqs.iterator().next() : -1
        );
    }

    private void init(List<Integer> input, int fieldSize) {
        for (int i = 0; i < input.size(); i++) {
            Integer element = input.get(i);

            if (element == null || element == 0) {
                possibleNumbers.add(i, generateListWithNumbersTill(fieldSize));
            } else {
                possibleNumbers.add(i, new ArrayList<>(Collections.singletonList(element)));
                solvedNumbersByPositions.put(i, element);
            }

            // optimizations
            Pair<Integer, Integer> coords = getCoords(i);

            rowsPositions.computeIfAbsent(coords.getKey(), k -> new ArrayList<>());
            rowsPositions.get(coords.getKey()).add(i);

            columnsPositions.computeIfAbsent(coords.getValue(), k -> new ArrayList<>());
            columnsPositions.get(coords.getValue()).add(i);

            int squarePos = getSquarePosFromPos(i);
            squaresPositions.computeIfAbsent(squarePos, k -> new ArrayList<>());
            squaresPositions.get(squarePos).add(i);
        }
    }

    private static List<Integer> generateListWithNumbersTill(int endInclusive) {
        List<Integer> list = new ArrayList<>();

        for (int i = 1; i <= endInclusive; i++) {
            list.add(i);
        }

        return list;
    }

    private Pair<Integer, Integer> getCoords(int pos) {
        return Pair.of(pos / fieldSize, pos % fieldSize);
    }

    private int getSquarePosFromPos(Integer solvedPosition) {
        Pair<Integer, Integer> squareCoords = getSquareCoords(solvedPosition);
        return toSquarePos(squareCoords.getKey(), squareCoords.getValue());
    }

    private Pair<Integer, Integer> getSquareCoords(int pos) {
        Pair<Integer, Integer> coords = getCoords(pos);
        return Pair.of((coords.getKey()) / smallSquaresSize, (coords.getValue()) / smallSquaresSize);
    }

    private int toSquarePos(int row, int col) {
        return row * smallSquaresInLine + col;
    }

    private int toPos(int row, int col) {
        return row * fieldSize + col;
    }

    @Getter
    @RequiredArgsConstructor
    public static class Solution {
        private final boolean solved;
        private final List<Integer> field;
        private final int fieldSize;
        private final int smallSquaresInLine;
    }

    @Getter
    @RequiredArgsConstructor
    private static class Place {
        private final int row;
        private final int column;
        private final int square;
    }
}
