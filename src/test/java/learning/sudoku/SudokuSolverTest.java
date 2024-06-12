package learning.sudoku;

import additional.Pair;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        SudokuSolver sudokuSolver = new SudokuSolver(template);
        SudokuSolver.Solution solution = sudokuSolver.solveSudoku();

        Assertions.assertTrue(solution.isSolved());

        Map<Integer, Set<Integer>> rowsValues = new HashMap<>();
        Map<Integer, Set<Integer>> columnsValues = new HashMap<>();
        Map<Integer, Set<Integer>> squaresValues = new HashMap<>();

        for (int i = 0; i < solution.getField().size(); i++) {
            Pair<Integer, Integer> coords = Pair.of(i / solution.getFieldSize(), i % solution.getFieldSize());

            rowsValues.computeIfAbsent(coords.getKey(), k -> new HashSet<>());
            rowsValues.get(coords.getKey()).add(solution.getField().get(i));

            columnsValues.computeIfAbsent(coords.getValue(), k -> new HashSet<>());
            columnsValues.get(coords.getValue()).add(solution.getField().get(i));

            int squarePos = coords.getKey() / solution.getSmallSquaresInLine() * solution.getSmallSquaresInLine()
                    + coords.getValue() / solution.getSmallSquaresInLine();
            squaresValues.computeIfAbsent(squarePos, k -> new HashSet<>());
            squaresValues.get(squarePos).add(solution.getField().get(i));
        }

        rowsValues.forEach((k, v) -> Assertions.assertEquals(solution.getFieldSize(), v.size()));
        columnsValues.forEach((k, v) -> Assertions.assertEquals(solution.getFieldSize(), v.size()));
        squaresValues.forEach((k, v) -> Assertions.assertEquals(solution.getFieldSize(), v.size()));
    }
}