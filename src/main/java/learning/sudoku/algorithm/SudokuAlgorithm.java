package learning.sudoku.algorithm;

import learning.sudoku.SudokuSolver;

public interface SudokuAlgorithm {
    String getName();

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