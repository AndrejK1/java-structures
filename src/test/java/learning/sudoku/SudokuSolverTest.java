package learning.sudoku;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
class SudokuSolverTest {

    @Test
    void testSolveSudokuMedium() {
        String fieldData =
                "050|100|000" +
                "204|000|093" +
                "000|003|450" +
                "-----------" +
                "721|038|640" +
                "430|057|981" +
                "000|061|002" +
                "-----------" +
                "000|004|009" +
                "105|300|800" +
                "640|802|000";

        solveAndValidate(fieldDataToIntegerList(fieldData));
    }

    @Test
    void testSolveSudokuMaster() {
        String fieldData =
                "100|500|709" +
                "008|000|002" +
                "060|900|000" +
                "-----------" +
                "010|240|060" +
                "007|060|000" +
                "600|091|000" +
                "-----------" +
                "000|609|430" +
                "000|074|081" +
                "000|000|000";

        solveAndValidate(fieldDataToIntegerList(fieldData));
    }

    @Test
    void testSolveSudokuExpert() {
        String fieldData =
                "700|003|001" +
                "930|008|500" +
                "000|009|002" +
                "-----------" +
                "000|000|000" +
                "300|100|005" +
                "400|007|080" +
                "-----------" +
                "500|600|004" +
                "029|000|000" +
                "004|700|200";

        solveAndValidate(fieldDataToIntegerList(fieldData));
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

    private static List<Integer> fieldDataToIntegerList(String fieldData) {
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < fieldData.length(); i++) {
            char ch = fieldData.charAt(i);

            if (Character.isDigit(ch)){
                list.add(Integer.valueOf(String.valueOf(ch)));
            }
        }

        return list;
    }

}