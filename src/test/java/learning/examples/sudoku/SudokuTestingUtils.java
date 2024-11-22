package learning.examples.sudoku;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@UtilityClass
public class SudokuTestingUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @SneakyThrows
    public static List<SudokuCase> readSudokuTestingCasesFromFile(String fileName) {
        return OBJECT_MAPPER.readValue(SudokuTestingUtils.class.getClassLoader().getResourceAsStream(fileName),
                new TypeReference<>() {});
    }

    public static boolean isSolutionCorrect(SudokuSolver.Solution solution) {
        return Stream.of(
                        solution.solution().getRowsPositions(),
                        solution.solution().getColumnsPositions(),
                        solution.solution().getSquaresPositions()
                )
                .flatMap(groupPositions -> groupPositions.values()
                        .stream()
                        .map(positions -> positions.stream().map(pos -> solution.solution().getField().get(pos)).collect(Collectors.toSet())))
                .toList()
                .stream()
                .allMatch(valuesInGroup -> solution.solution().getFieldWidth() == valuesInGroup.size() && !valuesInGroup.contains(0));
    }

    public static SudokuSolver.Solution solve(List<Integer> template) {
        SudokuSolver sudokuSolver = new SudokuSolver(new SudokuHolder(template));
        sudokuSolver.setSudokuPrinter(new TestingSudokuPrinter(true, false, true));
        return sudokuSolver.solveSudoku();
    }

    public static List<Integer> fieldDataToIntegerList(String fieldData) {
        return IntStream.range(0, fieldData.length())
                .mapToObj(fieldData::charAt)
                .filter(Character::isDigit)
                .map(String::valueOf)
                .map(Integer::valueOf)
                .toList();
    }

    public static String fieldDataFromList(List<Integer> field) {
        return field.stream().map(String::valueOf).collect(Collectors.joining());
    }
}
