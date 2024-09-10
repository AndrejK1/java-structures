package learning.sudoku.algorithm;

import learning.sudoku.EmptyPositionNoteException;
import learning.sudoku.SudokuSolver;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static learning.sudoku.SudokuUtils.buildLog;


/**
 * Tries to solve rest of the sudoku by brute force
 */
@Slf4j
@NoArgsConstructor
public class RecursiveGuesser implements SudokuAlgorithm {

    @Override
    public String getName() {
        return "Recursive Guesser";
    }

    @Override
    public int getPriority() {
        return 100;
    }

    @Override
    public Difficulty getDifficulty() {
        return Difficulty.GUESSING;
    }

    @Override
    public boolean runAlgorithmOnSudoku(SudokuSolver sudokuSolver) {
        guessNextUnsolvedPos(sudokuSolver);
        return true;
    }

    private boolean guessNextUnsolvedPos(SudokuSolver sudokuSolver) {
        int position = getAnyUnsolvedPos(sudokuSolver);

        if (position == -1) {
            return true;
        }

        List<List<Integer>> state = sudokuSolver.getState();

        List<Integer> possibleNumbers = new ArrayList<>(sudokuSolver.getPossibleNumbersByPosition(position));

        for (Integer possibleNumber : possibleNumbers) {
            try {
                log.info(buildLog(sudokuSolver, "Guessing", position, possibleNumber));
                sudokuSolver.updatePosition(position, possibleNumber);

                if (guessNextUnsolvedPos(sudokuSolver)) {
                    return true;
                }

                sudokuSolver.rewriteState(state);
            } catch (EmptyPositionNoteException ignore) {
                log.info(buildLog(sudokuSolver, "Guessing Rollback", position, possibleNumber));
                sudokuSolver.rewriteState(state);
            }
        }

        log.info(buildLog(sudokuSolver, "Guessing Complete Rollback", position, possibleNumbers));
        sudokuSolver.rewriteState(state);
        return false;
    }

    private int getAnyUnsolvedPos(SudokuSolver sudokuSolver) {
        for (int position = 0; position < sudokuSolver.getSudoku().getFieldCellCount(); position++) {
            if (sudokuSolver.getPossibleNumbersByPosition(position).size() > 1) {
                return position;
            }
        }

        return -1;
    }
}