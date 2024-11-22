package learning.examples.sudoku.algorithm;

import learning.examples.sudoku.SudokuHolder;
import learning.examples.sudoku.SudokuSolver;
import learning.examples.sudoku.SudokuUtils;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@NoArgsConstructor
public class HiddenSingleAndPointingGroupsCheck extends SudokuPositionGroupAlgorithm {

    @Override
    public String getName() {
        return "Hidden Single & Pointing Groups Finder";
    }

    @Override
    public int getPriority() {
        return 20;
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
                .collect(Collectors.toMap(Function.identity(), sudokuSolver::getPossibleNumbersByPosition));

        Map<Integer, List<Integer>> unsolvedNumbersWithPositions = SudokuUtils.findAllPossiblePositionsForUnsolvedNumbers(possibleNumbersByPos);

        for (Map.Entry<Integer, List<Integer>> numberWithPossiblePositions : unsolvedNumbersWithPositions.entrySet()) {
            Integer numberValue = numberWithPossiblePositions.getKey();
            List<Integer> numberPositions = numberWithPossiblePositions.getValue();

            if (numberPositions.size() == 1) {
                // hidden single check
                log.info(SudokuUtils.buildLog(sudokuSolver, "Hidden single", numberPositions.getFirst(), numberValue));
                sudokuSolver.updatePosition(numberPositions.getFirst(), numberValue);
                detectedChange = true;
            }

            // pointing groups check
            SudokuHolder.Place samePlacePositionsInfo = sudoku.isSamePlacePositions(numberPositions);

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
                    .map(pos -> {
                        if (sudokuSolver.getPossibleNumbersByPosition(pos).contains(numberValue)) {
                            log.info(SudokuUtils.buildLog(sudokuSolver, "Pointing Group", pos, numberValue));
                        }
                        return sudokuSolver.removeFromPosition(pos, numberValue);
                    })
                    .reduce(detectedChange, (r1, r2) -> r1 || r2);
        }

        return detectedChange;
    }
}