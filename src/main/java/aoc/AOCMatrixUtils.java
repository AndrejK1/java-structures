package aoc;

import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class AOCMatrixUtils {
    public static final int[] UP_DIRECTION = new int[]{-1, 0};
    public static final int[] DOWN_DIRECTION = new int[]{1, 0};
    public static final int[] LEFT_DIRECTION = new int[]{0, -1};
    public static final int[] RIGHT_DIRECTION = new int[]{0, 1};

    public static final List<int[]> DIRECTIONS = List.of(UP_DIRECTION, RIGHT_DIRECTION, DOWN_DIRECTION, LEFT_DIRECTION);

    public static boolean isOnField(int row, int column, int fieldWidth, int fieldHeight) {
        return !isOutsideOfField(row, column, fieldWidth, fieldHeight);
    }
    public static boolean isOutsideOfField(int row, int column, int fieldWidth, int fieldHeight) {
        return row < 0 || row >= fieldWidth || column < 0 || column >= fieldHeight;
    }

    public static int[] calcNewPosition(int[] coords, int[] direction) {
        return new int[]{coords[0] + direction[0], coords[1] + direction[1]};
    }

    public static int[] getCoordsFromPosition(int position, int fieldWidth) {
        return new int[]{position / fieldWidth, position % fieldWidth};
    }

    public static int getPositionFromCoords(int[] coords, int fieldWidth) {
        return getPositionFromCoords(coords[0], coords[1], fieldWidth);
    }

    public static int getPositionFromCoords(int row, int column, int fieldWidth) {
        return row * fieldWidth + column;
    }
}
