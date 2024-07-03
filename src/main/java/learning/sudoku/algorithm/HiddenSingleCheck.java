package learning.sudoku.algorithm;

import learning.sudoku.SudokuSolver;
import learning.sudoku.SudokuUtils;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@NoArgsConstructor
public class HiddenSingleCheck extends SudokuPositionGroupAlgorithm {

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

        Map<Integer, List<Integer>> possibleNumbersByPos = positionsToCheck.stream()
                .collect(Collectors.toMap(Function.identity(), sudokuSolver.getPossibleNumbersNotes()::get));

        Map<Integer, List<Integer>> unsolvedNumbersWithPositions = SudokuUtils.findAllPossiblePositionsForUnsolvedNumbers(possibleNumbersByPos);

        for (Map.Entry<Integer, List<Integer>> numberWithPossiblePositions : unsolvedNumbersWithPositions.entrySet()) {
            Integer numberValue = numberWithPossiblePositions.getKey();
            List<Integer> numberPositions = numberWithPossiblePositions.getValue();

            if (numberPositions.size() == 1) {
                sudokuSolver.getPossibleNumbersNotes().set(numberPositions.getFirst(), new ArrayList<>(List.of(numberValue)));
                detectedChange = true;
            }
        }

        return detectedChange;
    }

}