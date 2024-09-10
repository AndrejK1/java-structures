package learning.sudoku;

import learning.sudoku.algorithm.DefaultSudokuAlgorithmProvider;
import learning.sudoku.algorithm.SudokuAlgorithm;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
public class SudokuSolver {
    @Getter
    private final SudokuHolder sudoku;
    @Getter
    private final List<SudokuAlgorithm> algorithms;
    @Getter
    private final SudokuAlgorithm guessingAlgorithm;

    private final List<List<Integer>> possibleNumbersNotes;

    @Setter
    private SudokuPrinter sudokuPrinter;
    @Getter
    private int solutionStep;

    public SudokuSolver(SudokuHolder sudoku) {
        this(
                sudoku,
                DefaultSudokuAlgorithmProvider.getDefaultSudokuAlgorithms(),
                DefaultSudokuAlgorithmProvider.getDefaultGuessingAlgorithm()
        );
    }

    public SudokuSolver(SudokuHolder sudoku, List<SudokuAlgorithm> algorithms, SudokuAlgorithm guessingAlgorithm) {
        this.sudoku = sudoku;
        this.possibleNumbersNotes = new ArrayList<>(this.sudoku.getFieldCellCount());
        this.algorithms = algorithms.stream().sorted(Comparator.comparing(SudokuAlgorithm::getPriority)).toList();
        this.guessingAlgorithm = guessingAlgorithm;

        for (int i = 0; i < this.sudoku.getField().size(); i++) {
            possibleNumbersNotes.add(i, SudokuUtils.generateListWithNumbersTill(sudoku.getFieldWidth()));
        }
    }

    public Solution solveSudoku() {
        runInit();
        printState();

        SolutionStepInfo lastSolutionStepInfo;

        do {
            lastSolutionStepInfo = runSolutionStep();
            printState();
        } while (lastSolutionStepInfo.detectedChange() && !lastSolutionStepInfo.solved());

        if (!lastSolutionStepInfo.solved()) {
            lastSolutionStepInfo = runGuessingStep();
            printState();
        }

        return new Solution(lastSolutionStepInfo.solved(), solutionStep, new SudokuHolder(covertResult()));
    }

    public void runInit() {
        log.info("Running Initialization Process");

        for (int position = 0; position < this.sudoku.getField().size(); position++) {
            Integer element = this.sudoku.getField().get(position);

            if (element != null && element != 0) {
                updatePosition(position, element);
            }
        }

        solutionStep++;
    }

    public SolutionStepInfo runSolutionStep() {
        boolean detectedChange = false;
        boolean solved = false;

        for (SudokuAlgorithm algorithm : algorithms) {
            log.info("Running Algorithm: {}", algorithm.getName());
            detectedChange = runAlgorithmAndCheckForChange(algorithm) || detectedChange;
        }

        if (detectedChange) {
            solutionStep++;
            solved = isSolved();
        }

        return new SolutionStepInfo(solved, detectedChange, solutionStep, new SudokuHolder(covertResult()));
    }

    public SolutionStepInfo runGuessingStep() {
        log.info("Running Algorithm: {}", guessingAlgorithm.getClass().getName());
        runAlgorithmAndCheckForChange(guessingAlgorithm);

        solutionStep++;
        boolean solved = isSolved();

        return new SolutionStepInfo(solved, true, solutionStep, new SudokuHolder(covertResult()));
    }

    public boolean runAlgorithmAndCheckForChange(SudokuAlgorithm algorithm) {
        return algorithm.runAlgorithmOnSudoku(this);
    }

    public boolean isSolved() {
        return possibleNumbersNotes.stream().filter(p -> p.size() == 1).count() == sudoku.getFieldCellCount();
    }

    private List<Integer> covertResult() {
        return possibleNumbersNotes.stream()
                .map(list -> list.size() == 1 ? list.getFirst() : 0)
                .toList();
    }

    /**
     * Rewrites solver calculated notes
     *
     * @param possibleNumbersNotes new solver notes
     */
    public void rewriteState(List<List<Integer>> possibleNumbersNotes) {
        this.possibleNumbersNotes.clear();
        this.possibleNumbersNotes.addAll(possibleNumbersNotes);
    }

    /**
     * Retrieves copy (high-level only) for solver calculated notes
     */
    public List<List<Integer>> getState() {
        return new ArrayList<>(possibleNumbersNotes);
    }

    public boolean removeFromPosition(int position, Integer value) {
        return removeFromPosition(position, Collections.singletonList(value));
    }

    public List<Integer> getPossibleNumbersByPosition(int position) {
        return possibleNumbersNotes.get(position);
    }

    public boolean removeFromPosition(int position, List<Integer> values) {
        ArrayList<Integer> currentValues = new ArrayList<>(possibleNumbersNotes.get(position));

        if (currentValues.removeAll(values)) {
            updatePosition(position, currentValues);
            return true;
        }

        return false;
    }

    public boolean updatePosition(int position, Integer value) {
        return updatePosition(position, Collections.singletonList(value));
    }

    public boolean updatePosition(int position, List<Integer> values) {
        boolean detectedChange = false;
        ArrayList<Integer> updatedValues = new ArrayList<>(values);

        if (updatedValues.isEmpty()) {
            throw new EmptyPositionNoteException(position);
        }

        if (possibleNumbersNotes.get(position).equals(updatedValues)) {
            return detectedChange;
        }

        possibleNumbersNotes.set(position, updatedValues);

        if (updatedValues.size() == 1) {
            for (Integer positionToUpdate : sudoku.getAllPositionsInPlace(position)) {
                if (positionToUpdate == position) {
                    continue;
                }

                detectedChange = removeFromPosition(positionToUpdate, updatedValues.getFirst()) || detectedChange;
            }
        }

        return detectedChange;
    }

    private void printState() {
        if (sudokuPrinter != null) {
            sudokuPrinter.printState(this);
        }
    }

    public record Solution(boolean solved, int solutionStep, SudokuHolder solution) {
    }

    public record SolutionStepInfo(boolean solved, boolean detectedChange, int solutionStep,
                                   SudokuHolder currentState) {
    }
}
