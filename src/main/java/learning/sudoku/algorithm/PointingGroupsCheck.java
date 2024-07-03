package learning.sudoku.algorithm;

import learning.sudoku.SudokuHolder;
import learning.sudoku.SudokuSolver;
import learning.sudoku.SudokuUtils;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@NoArgsConstructor
public class PointingGroupsCheck extends SudokuPositionGroupAlgorithm {

    @Override
    public int getPriority() {
        return 30;
    }

    @Override
    public Difficulty getDifficulty() {
        return Difficulty.INTERMEDIATE;
    }

    @Override
    protected boolean runDeepCheckAtPositions(SudokuSolver sudokuSolver, List<Integer> positionsToCheck) {
        boolean detectedChange = false;
        SudokuHolder sudoku = sudokuSolver.getSudoku();

        Map<Integer, List<Integer>> possibleNumbersByPos = positionsToCheck.stream()
                .collect(Collectors.toMap(Function.identity(), sudokuSolver.getPossibleNumbersNotes()::get));

        Map<Integer, List<Integer>> unsolvedNumbersWithPositions = SudokuUtils.findAllPossiblePositionsForUnsolvedNumbers(possibleNumbersByPos);

        for (Map.Entry<Integer, List<Integer>> numberWithPossiblePositions : unsolvedNumbersWithPositions.entrySet()) {
            Integer numberValue = numberWithPossiblePositions.getKey();
            List<Integer> numberPositions = numberWithPossiblePositions.getValue();

            if (numberPositions.size() == 1) {
                continue;
            }

            SudokuHolder.Place samePlacePositionsInfo = sudokuSolver.getSudoku().isSamePlacePositions(numberPositions);

            List<Integer> samePlacePositions = new ArrayList<>();

            if (samePlacePositionsInfo.row() != -1) {
                samePlacePositions.addAll(sudoku.getRowPositions(samePlacePositionsInfo.row()));
            }

            if (samePlacePositionsInfo.column() != -1) {
                samePlacePositions.addAll(sudoku.getColumnPositions(samePlacePositionsInfo.column()));
            }

            if (samePlacePositionsInfo.square() != -1) {
                samePlacePositions.addAll(sudoku.getSquarePositions(samePlacePositionsInfo.square()));
            }

            detectedChange = samePlacePositions
                    .stream()
                    .filter(pos -> !numberPositions.contains(pos))
                    .map(sudokuSolver.getPossibleNumbersNotes()::get)
                    .map(pos -> pos.remove(numberValue))
                    .reduce(detectedChange, (r1, r2) -> r1 || r2);
        }

        return detectedChange;
    }
}