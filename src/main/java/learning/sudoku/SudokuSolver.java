package learning.sudoku;

import learning.sudoku.algorithm.DefaultSudokuAlgorithmProvider;
import learning.sudoku.algorithm.SudokuAlgorithm;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class SudokuSolver {
    private final SudokuHolder sudoku;
    private final List<List<Integer>> possibleNumbersNotes;
    private final Map<Integer, Integer> solvedNumbersByPositions;
    private final List<SudokuAlgorithm> algorithms;
    @Setter
    private SudokuPrinter sudokuPrinter;
    private int solutionStep;

    public SudokuSolver(SudokuHolder sudoku) {
        this(sudoku, DefaultSudokuAlgorithmProvider.getDefaultSudokuAlgorithms());
    }

    public SudokuSolver(SudokuHolder sudoku, List<SudokuAlgorithm> algorithms) {
        this.sudoku = sudoku;
        this.solvedNumbersByPositions = new HashMap<>();
        this.possibleNumbersNotes = new ArrayList<>(this.sudoku.getFieldCellCount());
        this.algorithms = algorithms;

        for (int i = 0; i < this.sudoku.getField().size(); i++) {
            Integer element = this.sudoku.getField().get(i);

            if (element == null || element == 0) {
                possibleNumbersNotes.add(i, SudokuUtils.generateListWithNumbersTill(sudoku.getFieldWidth()));
            } else {
                possibleNumbersNotes.add(i, new ArrayList<>(Collections.singletonList(element)));
                solvedNumbersByPositions.put(i, element);
            }
        }
    }

    public Solution solveSudoku() {
        printState();

        boolean detectedChange;
        boolean solved = false;

        do {
            detectedChange = false;


            for (SudokuAlgorithm algorithm : algorithms) {
                detectedChange = algorithm.runAlgorithmOnSudoku(this) || detectedChange;

                if (detectedChange) {
                    updateSolvedPositionsFromNotes();
                }
            }

            if (detectedChange) {
                solutionStep++;
                solved = isSolved();
            }

            printState();
        } while (detectedChange && !solved);

        return new Solution(solved, solutionStep, new SudokuHolder(covertResult()));
    }

    public boolean isSolved() {
        return solvedNumbersByPositions.size() == sudoku.getFieldCellCount();
    }

    private List<Integer> covertResult() {
        return possibleNumbersNotes.stream()
                .map(list -> list.size() == 1 ? list.getFirst() : 0)
                .toList();
    }

    private void updateSolvedPositionsFromNotes() {
        for (int position = 0; position < possibleNumbersNotes.size(); position++) {
            List<Integer> possibleValuesAtPos = possibleNumbersNotes.get(position);

            if (possibleValuesAtPos.isEmpty()) {
                throw new IllegalStateException("0 possible situations at position " + position);
            }

            if (possibleValuesAtPos.size() == 1) {
                solvedNumbersByPositions.putIfAbsent(position, possibleValuesAtPos.getFirst());
            }
        }
    }

    private void printState() {
        if (sudokuPrinter != null) {
            sudokuPrinter.printState(this);
        }
    }

    public record Solution(boolean solved, int solutionStep, SudokuHolder solution) {
    }
}
