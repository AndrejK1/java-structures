package aoc.aoc2024;

import aoc.AOCTask;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static aoc.aoc2024.AOC6GuardGallivant.GuardGallivantUtils.UP;
import static aoc.aoc2024.AOC6GuardGallivant.GuardGallivantUtils.calcNewPosition;
import static aoc.aoc2024.AOC6GuardGallivant.GuardGallivantUtils.getCoordsFromPosition;
import static aoc.aoc2024.AOC6GuardGallivant.GuardGallivantUtils.getNextDirection;
import static aoc.aoc2024.AOC6GuardGallivant.GuardGallivantUtils.getPositionFromCoords;
import static aoc.aoc2024.AOC6GuardGallivant.GuardGallivantUtils.isOutsideOfField;

@Slf4j
public class AOC6GuardGallivant extends AOCTask<AOC6GuardGallivant.AOC6InputData> {

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
        int[] currentDirection = UP;

        Set<Integer> guardUniquePositions = new HashSet<>();
        int possibleCircles = 0;

        while (!isOutsideOfField(guardCoords, fieldWidth, fieldHeight)) {
            guardUniquePositions.add(getPositionFromCoords(guardCoords, fieldWidth));

            int[] nextPositionCoords = calcNewPosition(guardCoords, currentDirection);

            if (isOutsideOfField(nextPositionCoords, fieldWidth, fieldHeight)) {
                break;
            }

            int nextPosition = getPositionFromCoords(nextPositionCoords, fieldWidth);

            if (wallPositions.contains(nextPosition)) {
                currentDirection = getNextDirection(currentDirection);
                continue;
            }

            guardCoords = nextPositionCoords;
        }

        for (Integer guardUniquePosition : guardUniquePositions) {
            if (guardUniquePosition == inputData.guardPosition()) {
                continue;
            }

            guardCoords = getCoordsFromPosition(inputData.guardPosition(), fieldWidth);
            currentDirection = UP;

            Map<Integer, int[]> guardUniquePositionsInCircle = new HashMap<>();

            wallPositions.add(guardUniquePosition);

            while (!isOutsideOfField(guardCoords, fieldWidth, fieldHeight)) {
                int guardPosition = getPositionFromCoords(guardCoords, fieldWidth);

                if (currentDirection == guardUniquePositionsInCircle.get(guardPosition)) {
                    possibleCircles++;
                    break;
                }

                guardUniquePositionsInCircle.putIfAbsent(guardPosition, currentDirection);

                int[] nextPositionCoords = calcNewPosition(guardCoords, currentDirection);

                if (isOutsideOfField(nextPositionCoords, fieldWidth, fieldHeight)) {
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
    protected AOC6InputData parseInputData(String fileContent) {
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

    public record AOC6InputData(int fieldWidth,
                                int fieldHeight,
                                Set<Integer> wallPositions,
                                int guardPosition) implements AOCInputData {
    }

    @UtilityClass
    static final class GuardGallivantUtils {
        static final int[] UP = new int[]{-1, 0};
        static final int[] DOWN = new int[]{1, 0};
        static final int[] LEFT = new int[]{0, -1};
        static final int[] RIGHT = new int[]{0, 1};
        static final List<int[]> DIRECTION_ORDER = List.of(UP, RIGHT, DOWN, LEFT);

        static boolean isOutsideOfField(int[] coords, int fieldWidth, int fieldHeight) {
            return coords[0] < 0 || coords[0] >= fieldWidth || coords[1] < 0 || coords[1] >= fieldHeight;
        }

        static int[] calcNewPosition(int[] coords, int[] direction) {
            return new int[]{coords[0] + direction[0], coords[1] + direction[1]};
        }

        static int[] getNextDirection(int[] current) {
            return DIRECTION_ORDER.get((DIRECTION_ORDER.indexOf(current) + 1) % DIRECTION_ORDER.size());
        }

        static int[] getCoordsFromPosition(int position, int fieldWidth) {
            return new int[]{position / fieldWidth, position % fieldWidth};
        }

        static int getPositionFromCoords(int[] coords, int fieldWidth) {
            return getPositionFromCoords(coords[0], coords[1], fieldWidth);
        }

        static int getPositionFromCoords(int row, int column, int fieldWidth) {
            return row * fieldWidth + column;
        }
    }
}
