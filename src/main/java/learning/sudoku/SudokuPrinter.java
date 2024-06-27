package learning.sudoku;

import java.util.List;
import java.util.Map;

public interface SudokuPrinter {
    void printState(int solutionStep, Map<Integer, Integer> solvedNumbersByPositions, List<List<Integer>> possibleNumbers, int fieldSize, boolean isSolved);
}
