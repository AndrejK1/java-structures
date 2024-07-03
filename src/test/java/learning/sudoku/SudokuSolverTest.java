package learning.sudoku;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
class SudokuSolverTest {

    @Test
    void testSolveSudokuMedium() {
        List<Integer> template = List.of(
                0, 5, 0, 1, 0, 0, 0, 0, 0,
                2, 0, 4, 0, 0, 0, 0, 9, 3,
                0, 0, 0, 0, 0, 3, 4, 5, 0,
                7, 2, 1, 0, 3, 8, 6, 4, 0,
                4, 3, 0, 0, 5, 7, 9, 8, 1,
                0, 0, 0, 0, 6, 1, 0, 0, 2,
                0, 0, 0, 0, 0, 4, 0, 0, 9,
                1, 0, 5, 3, 0, 0, 8, 0, 0,
                6, 4, 0, 8, 0, 2, 0, 0, 0
        );

        solveAndValidate(template);
    }

    @Test
    void testSolveSudokuMaster() {
        List<Integer> template = List.of(
                1, 0, 0, 5, 0, 0, 7, 0, 9,
                0, 0, 8, 0, 0, 0, 0, 0, 2,
                0, 6, 0, 9, 0, 0, 0, 0, 0,
                0, 1, 0, 2, 4, 0, 0, 6, 0,
                0, 0, 7, 0, 6, 0, 0, 0, 0,
                6, 0, 0, 0, 9, 1, 0, 0, 0,
                0, 0, 0, 6, 0, 9, 4, 3, 0,
                0, 0, 0, 0, 7, 4, 0, 8, 1,
                0, 0, 0, 0, 0, 0, 0, 0, 0
        );

        solveAndValidate(template);
    }

    @Test
    void testSolveSudokuExpert() {
        List<Integer> template = List.of(
                7, 0, 0, 0, 0, 3, 0, 0, 1,
                9, 3, 0, 0, 0, 8, 5, 0, 0,
                0, 0, 0, 0, 0, 9, 0, 0, 2,
                0, 0, 0, 0, 0, 0, 0, 0, 0,
                3, 0, 0, 1, 0, 0, 0, 0, 5,
                4, 0, 0, 0, 0, 7, 0, 8, 0,
                5, 0, 0, 6, 0, 0, 0, 0, 4,
                0, 2, 9, 0, 0, 0, 0, 0, 0,
                0, 0, 4, 7, 0, 0, 2, 0, 0
        );

        solveAndValidate(template);
    }

    private static void solveAndValidate(List<Integer> template) {
        SudokuSolver sudokuSolver = new SudokuSolver(new SudokuHolder(template));
        sudokuSolver.setSudokuPrinter(new TestingSudokuPrinter(true, false, true));

        SudokuSolver.Solution solution = sudokuSolver.solveSudoku();

        Assertions.assertTrue(solution.solved());

        List<Set<Integer>> allGroupsUniqueValues = Stream.of(solution.solution().getRowsPositions(),
                        solution.solution().getColumnsPositions(),
                        solution.solution().getSquaresPositions())
                .flatMap(groupPositions -> groupPositions.values()
                        .stream()
                        .map(positions -> positions.stream().map(pos -> solution.solution().getField().get(pos)).collect(Collectors.toSet())))
                .toList();

        allGroupsUniqueValues.forEach(valuesInGroup -> Assertions.assertEquals(solution.solution().getFieldWidth(), valuesInGroup.size()));
    }

    private record TestingSudokuPrinter(boolean printMove,
                                        boolean printPossibleNumbers,
                                        boolean printSolved) implements SudokuPrinter {
        @Override
        public void printState(SudokuSolver sudokuSolver) {

            if (printMove || (printSolved && sudokuSolver.isSolved())) {
                StringBuilder stringBuilder = new StringBuilder();

                for (int i = 0; i < sudokuSolver.getSudoku().getFieldCellCount(); i++) {
                    Integer value = sudokuSolver.getSolvedNumbersByPositions().getOrDefault(i, 0);

                    stringBuilder.append(value == 0 ? "-" : value);

                    if ((i + 1) % sudokuSolver.getSudoku().getFieldWidth() == 0) {
                        stringBuilder.append('\n');
                    } else {
                        stringBuilder.append(' ');
                    }
                }

                log.info("Step: {}, Solved: {}, Solved positions\n{}", sudokuSolver.getSolutionStep(), sudokuSolver.isSolved(), stringBuilder);
            }

            if (printPossibleNumbers) {
                StringBuilder stringBuilder = new StringBuilder();

                int maxStrLength = sudokuSolver.getPossibleNumbersNotes().stream()
                        .map(Object::toString)
                        .map(s -> s.replace(" ", ""))
                        .mapToInt(String::length)
                        .max()
                        .orElse(0);

                for (int i = 0; i < sudokuSolver.getPossibleNumbersNotes().size(); i++) {
                    String value = sudokuSolver.getPossibleNumbersNotes().get(i).toString().replace(" ", "");

                    stringBuilder.append(value);

                    stringBuilder.append(" ".repeat(Math.max(0, maxStrLength - value.length())));

                    if ((i + 1) % sudokuSolver.getSudoku().getFieldWidth() == 0) {
                        stringBuilder.append('\n');
                    } else {
                        stringBuilder.append(' ');
                    }
                }

                log.info("Step: {}, Solved: {}, Possible Numbers\n{}", sudokuSolver.getSolutionStep(), sudokuSolver.isSolved(), stringBuilder);
            }
        }
    }

}