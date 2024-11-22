package learning.examples.sudoku;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
class SudokuSolverTest {

    @Test
    void testScenariosFromFile() {
        List<SudokuCase> sudokuCases = SudokuTestingUtils.readSudokuTestingCasesFromFile("sudoku.json");

        Set<String> failedToSolve = new HashSet<>();

        for (int i = 0; i < sudokuCases.size(); i++) {
            SudokuCase sudokuCase = sudokuCases.get(i);
            log.info("---- SOLVING SUDOKU # {} ----", i);
            log.info("Sudoku mission {}", sudokuCase.getMission());

            SudokuSolver.Solution solution = SudokuTestingUtils.solve(SudokuTestingUtils.fieldDataToIntegerList(sudokuCase.getMission()));

            if (!solution.solved()) {
                log.error("==== SUDOKU #{} was NOT SOLVED! ====", i);
                failedToSolve.add(sudokuCase.getMission());
            } else if (!SudokuTestingUtils.isSolutionCorrect(solution)) {
                log.error("==== SUDOKU #{} has  INCORRECT SOLUTION! ====", i);
                failedToSolve.add(sudokuCase.getMission());
            } else {
                String fieldData = SudokuTestingUtils.fieldDataFromList(solution.solution().getField());
                log.info("==== SUDOKU #{} was SOLVED (" + (sudokuCase.getSolution().equals(fieldData) ? "matching" : "alternate") + " solution)! ====", i);
            }
        }

        failedToSolve.forEach(mission -> log.error("Failed to solve mission: {}", mission));
        Assertions.assertTrue(failedToSolve.isEmpty(), "Failed to solve " + failedToSolve.size() + "/" + sudokuCases.size() + " cases");
    }

    @Test
    void testSolvingLargeBlankSudoku() {
        String blankSudoku = "0".repeat(256);
        SudokuSolver.Solution solution = SudokuTestingUtils.solve(SudokuTestingUtils.fieldDataToIntegerList(blankSudoku));

        Assertions.assertTrue(solution.solved());
        Assertions.assertTrue(SudokuTestingUtils.isSolutionCorrect(solution));
    }
}