package learning.sudoku.algorithm;

import additional.Pair;
import learning.sudoku.SudokuHolder;
import learning.sudoku.SudokuSolver;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


/**
 * Eliminates impossible number notes just by checking rows, columns, and squares with solved positions
 * This method includes next techniques: Last Free Cell, Last Remaining Cell, Last Possible Number, Obvious Singles
 */
@NoArgsConstructor
public class SinglePositionCheck implements SudokuAlgorithm {

    @Override
    public int getPriority() {
        return 10;
    }

    @Override
    public Difficulty getDifficulty() {
        return Difficulty.NOVICE;
    }

    @Override
    public boolean runAlgorithmOnSudoku(SudokuSolver sudokuSolver) {
        SudokuHolder sudoku = sudokuSolver.getSudoku();
        boolean detectedChange = false;

        for (Map.Entry<Integer, Integer> entry : sudokuSolver.getSolvedNumbersByPositions().entrySet()) {
            Integer solvedPosition = entry.getKey();
            Integer solvedValue = entry.getValue();

            Pair<Integer, Integer> solvedValueCoords = sudoku.getPositionCoordinates(solvedPosition);

            // get all coords that are related to solved position and run number deletion from possible options
            detectedChange = Stream.of(
                            sudoku.getRowPositions(solvedValueCoords.getKey()),
                            sudoku.getColumnPositions(solvedValueCoords.getValue()),
                            sudoku.getSquarePositions(sudoku.getSquarePosition(solvedPosition)))
                    .flatMap(List::stream)
                    .filter(positionToCheck -> !positionToCheck.equals(solvedPosition))
                    .map(sudokuSolver.getPossibleNumbersNotes()::get)
                    .map(numbers -> numbers.remove(solvedValue))
                    .reduce(detectedChange, (r1, r2) -> r1 || r2);
        }

        return detectedChange;
    }
}