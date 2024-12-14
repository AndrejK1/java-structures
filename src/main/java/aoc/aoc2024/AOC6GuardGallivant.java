package aoc.aoc2024;

import aoc.AOCTask;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static aoc.AOCMatrixUtils.DIRECTIONS;
import static aoc.AOCMatrixUtils.UP_DIRECTION;
import static aoc.AOCMatrixUtils.calcNewPosition;
import static aoc.AOCMatrixUtils.getCoordsFromPosition;
import static aoc.AOCMatrixUtils.getPositionFromCoords;
import static aoc.AOCMatrixUtils.isOutsideOfField;

public class AOC6GuardGallivant implements AOCTask<AOC6GuardGallivant.AOC6InputData> {

    @Override
    public String getTaskTitle() {
        return "Day 6: Guard Gallivant";
    }

    @Override
    public AOCAnswer solve(AOC6InputData inputData) {
        int fieldWidth = inputData.fieldWidth();
        int fieldHeight = inputData.fieldHeight();
        Set<Integer> wallPositions = inputData.wallPositions();

        int[] guardCoords = getCoordsFromPosition(inputData.guardPosition(), fieldWidth);
        int[] currentDirection = UP_DIRECTION;

        Set<Integer> guardUniquePositions = new HashSet<>();
        int possibleCircles = 0;

        while (!isOutsideOfField(guardCoords[0], guardCoords[1], fieldWidth, fieldHeight)) {
            guardUniquePositions.add(getPositionFromCoords(guardCoords, fieldWidth));

            int[] nextPositionCoords = calcNewPosition(guardCoords, currentDirection);

            if (isOutsideOfField(nextPositionCoords[0], nextPositionCoords[1], fieldWidth, fieldHeight)) {
                break;
            }

            int nextPosition = getPositionFromCoords(nextPositionCoords, fieldWidth);

            if (wallPositions.contains(nextPosition)) {
                currentDirection = getNextDirection(currentDirection);
            } else {
                guardCoords = nextPositionCoords;
            }
        }

        for (Integer guardUniquePosition : guardUniquePositions) {
            if (guardUniquePosition == inputData.guardPosition()) {
                continue;
            }

            guardCoords = getCoordsFromPosition(inputData.guardPosition(), fieldWidth);
            currentDirection = UP_DIRECTION;

            Map<Integer, int[]> guardUniquePositionsInCircle = new HashMap<>();

            wallPositions.add(guardUniquePosition);

            while (!isOutsideOfField(guardCoords[0], guardCoords[1], fieldWidth, fieldHeight)) {
                int guardPosition = getPositionFromCoords(guardCoords, fieldWidth);

                if (currentDirection == guardUniquePositionsInCircle.get(guardPosition)) {
                    possibleCircles++;
                    break;
                }

                guardUniquePositionsInCircle.putIfAbsent(guardPosition, currentDirection);

                int[] nextPositionCoords = calcNewPosition(guardCoords, currentDirection);

                if (isOutsideOfField(nextPositionCoords[0], nextPositionCoords[1], fieldWidth, fieldHeight)) {
                    break;
                }

                int nextPos = getPositionFromCoords(nextPositionCoords, fieldWidth);

                if (wallPositions.contains(nextPos)) {
                    currentDirection = getNextDirection(currentDirection);
                } else {
                    guardCoords = nextPositionCoords;
                }
            }

            wallPositions.remove(guardUniquePosition);
        }

        return new AOCAnswer(guardUniquePositions.size(), possibleCircles);
    }

    @Override
    public AOC6InputData parseInputData(String fileContent) {
        Set<Integer> wallPositions = new HashSet<>();
        int guardPosition = -1;

        String[] rows = fileContent.split("\n");
        int fieldWidth = rows[0].length();

        for (int i = 0; i < rows.length; i++) {
            String row = rows[i];

            for (int j = 0; j < row.length(); j++) {
                if (row.charAt(j) == '#') {
                    wallPositions.add(getPositionFromCoords(i, j, fieldWidth));
                }

                if (row.charAt(j) == '^') {
                    guardPosition = getPositionFromCoords(i, j, fieldWidth);
                }
            }
        }

        return new AOC6InputData(
                fieldWidth,
                rows.length,
                wallPositions,
                guardPosition
        );
    }

    private int[] getNextDirection(int[] current) {
        return DIRECTIONS.get((DIRECTIONS.indexOf(current) + 1) % DIRECTIONS.size());
    }

    public record AOC6InputData(int fieldWidth,
                                int fieldHeight,
                                Set<Integer> wallPositions,
                                int guardPosition) implements AOCInputData {
    }
}
