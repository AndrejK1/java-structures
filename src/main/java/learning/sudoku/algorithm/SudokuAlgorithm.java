package learning.sudoku.algorithm;

import learning.sudoku.SudokuSolver;

public interface SudokuAlgorithm {
    int getPriority();

    Difficulty getDifficulty();

    boolean runAlgorithmOnSudoku(SudokuSolver sudokuSolver);

    enum Difficulty {
        NOVICE,
        INTERMEDIATE,
        ADVANCED,
        GUESSING
    }
}