package aoc.aoc2024;

import additional.Pair;
import aoc.AOCTask;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AOC4CeresSearch implements AOCTask<AOC4CeresSearch.AOC4InputData> {

    private static final char[] XMAS = {'X', 'M', 'A', 'S'};

    @Override
    public String getTaskTitle() {
        return "Day 4: Ceres Search";
    }

    @Override
    public AOCAnswer solve(AOC4InputData inputData) {
        char[][] matrix = inputData.charMatrix();
        List<Pair<Integer, Integer>> xCoords = inputData.xCoords();
        List<Pair<Integer, Integer>> aCoords = inputData.aCoords();

        int straitWordsCount = 0;
        int xmasWordsCount = 0;

        for (Pair<Integer, Integer> wordStartCoords : xCoords) {
            straitWordsCount += countStraitWordsFromPosition(matrix, wordStartCoords.getKey(), wordStartCoords.getValue());
        }

        for (Pair<Integer, Integer> wordStartCoords : aCoords) {
            xmasWordsCount += countXmasWordsFromPosition(matrix, wordStartCoords.getKey(), wordStartCoords.getValue());
        }

        return new AOCAnswer(
                straitWordsCount,
                xmasWordsCount
        );
    }

    private int countStraitWordsFromPosition(char[][] matrix, int line, int column) {
        int result = 0;
        int matrixRows = matrix.length;
        int matrixColumns = matrix[0].length;

        if (line >= 3) {
            result += countStraitWord(matrix, generateStraitWordCoordinates(line, column, -1, 0));

            if (column >= 3) {
                result += countStraitWord(matrix, generateStraitWordCoordinates(line, column, -1, -1));
            }

            if (column + 3 < matrixColumns) {
                result += countStraitWord(matrix, generateStraitWordCoordinates(line, column, -1, 1));
            }
        }

        if (column >= 3) {
            result += countStraitWord(matrix, generateStraitWordCoordinates(line, column, 0, -1));
        }

        if (column + 3 < matrixColumns) {
            result += countStraitWord(matrix, generateStraitWordCoordinates(line, column, 0, 1));
        }

        if (line + 3 < matrixRows) {
            result += countStraitWord(matrix, generateStraitWordCoordinates(line, column, 1, 0));

            if (column >= 3) {
                result += countStraitWord(matrix, generateStraitWordCoordinates(line, column, 1, -1));
            }

            if (column + 3 < matrixColumns) {
                result += countStraitWord(matrix, generateStraitWordCoordinates(line, column, 1, 1));
            }
        }

        return result;
    }

    private int[][] generateStraitWordCoordinates(int line, int column, int vDirection, int hDirection) {
        int[][] wordCoords = new int[XMAS.length][2];

        for (int i = 0; i < XMAS.length; i++) {
            wordCoords[i][0] = line + i * vDirection;
            wordCoords[i][1] = column + i * hDirection;
        }

        return wordCoords;
    }

    private int countStraitWord(char[][] matrix, int[][] coords) {
        for (int i = 0; i < coords.length; i++) {
            if (matrix[coords[i][0]][coords[i][1]] != XMAS[i]) {
                return 0;
            }
        }
        return 1;
    }

    private int countXmasWordsFromPosition(char[][] matrix, int line, int column) {
        int matrixRows = matrix.length;
        int matrixColumns = matrix[0].length;

        if (line <= 0 || column <= 0 || line + 1 >= matrixRows || column + 1 >= matrixColumns) {
            return 0;
        }

        Set<Character> firstDiag = new HashSet<>();
        firstDiag.add(matrix[line - 1][column - 1]);
        firstDiag.add(matrix[line + 1][column + 1]);

        Set<Character> secondDiag = new HashSet<>();
        secondDiag.add(matrix[line - 1][column + 1]);
        secondDiag.add(matrix[line + 1][column - 1]);

        return firstDiag.contains('M') && firstDiag.contains('S')
                && secondDiag.contains('M') && secondDiag.contains('S') ? 1 : 0;
    }

    @Override
    public AOC4InputData parseInputData(String fileContent) {
        String[] linesCount = fileContent.split("\n");
        int lineLength = linesCount[0].length();

        char[][] matrix = new char[linesCount.length][lineLength];
        List<Pair<Integer, Integer>> xCoords = new ArrayList<>();
        List<Pair<Integer, Integer>> aCoords = new ArrayList<>();

        for (int i = 0; i < linesCount.length; i++) {
            for (int j = 0; j < lineLength; j++) {
                char character = linesCount[i].charAt(j);
                matrix[i][j] = character;

                if (character == 'X') {
                    xCoords.add(Pair.of(i, j));
                }

                if (character == 'A') {
                    aCoords.add(Pair.of(i, j));
                }
            }
        }

        return new AOC4InputData(
                matrix,
                xCoords,
                aCoords
        );
    }

    public record AOC4InputData(char[][] charMatrix,
                                List<Pair<Integer, Integer>> xCoords,
                                List<Pair<Integer, Integer>> aCoords) implements AOCInputData {
    }
}
