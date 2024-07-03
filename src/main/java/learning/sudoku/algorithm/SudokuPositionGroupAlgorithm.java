package learning.sudoku.algorithm;

import learning.sudoku.SudokuSolver;

import java.util.List;

/**
 * Check on rows, columns, squares using more advanced techniques: Hidden Singles, Obvious Groups, Hidden Groups, Pointing Groups
 */
public abstract class SudokuPositionGroupAlgorithm implements SudokuAlgorithm {

    @Override
    public boolean runAlgorithmOnSudoku(SudokuSolver sudokuSolver) {
        boolean detectedChange = false;

        for (List<Integer> squarePositions : sudokuSolver.getSudoku().getRowsPositions().values()) {
            detectedChange = runDeepCheckAtPositions(sudokuSolver, squarePositions) || detectedChange;
        }

        for (List<Integer> positions : sudokuSolver.getSudoku().getColumnsPositions().values()) {
            detectedChange = runDeepCheckAtPositions(sudokuSolver, positions) || detectedChange;
        }

        for (List<Integer> positions : sudokuSolver.getSudoku().getSquaresPositions().values()) {
            detectedChange = runDeepCheckAtPositions(sudokuSolver, positions) || detectedChange;
        }

        return detectedChange;
    }

    protected abstract boolean runDeepCheckAtPositions(SudokuSolver sudokuSolver, List<Integer> positionsToCheck);

}