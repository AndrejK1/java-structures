package aoc.aoc2024;

import aoc.AOCTask;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static aoc.AOCMatrixUtils.DIRECTIONS;
import static aoc.AOCMatrixUtils.calcNewPosition;
import static aoc.AOCMatrixUtils.getCoordsFromPosition;
import static aoc.AOCMatrixUtils.getPositionFromCoords;
import static aoc.AOCMatrixUtils.isOnField;

public class AOC10HoofIt implements AOCTask<AOC10HoofIt.AOC10InputData> {

    @Override
    public String getTaskTitle() {
        return "Day 10: Hoof It";
    }

    @Override
    public AOCAnswer solve(AOC10InputData inputData) {
        long result1 = 0;
        long result2 = 0;

        for (Integer zeroPosition : inputData.zeroPositions()) {
            List<Integer> reachedPeakPositions = countHikingTrails(zeroPosition, inputData);

            result1 += new HashSet<>(reachedPeakPositions).size();
            result2 += reachedPeakPositions.size();
        }

        return new AOCAnswer(result1, result2);
    }

    private List<Integer> countHikingTrails(Integer zeroPosition, AOC10InputData inputData) {
        List<Integer> reachedPeaks = new ArrayList<>();
        backTrackHikingTrails(0, zeroPosition, reachedPeaks, inputData);
        return reachedPeaks;
    }

    private void backTrackHikingTrails(Integer currentNumber,
                                       Integer currentPosition,
                                       List<Integer> reachedPeaks,
                                       AOC10InputData inputData) {
        if (currentNumber == 9) {
            reachedPeaks.add(currentPosition);
            return;
        }

        List<Integer> nextHikingPositions = findNextHikingPositions(currentPosition, currentNumber + 1, inputData);

        for (Integer nextHikingPosition : nextHikingPositions) {
            backTrackHikingTrails(currentNumber + 1, nextHikingPosition, reachedPeaks, inputData);
        }
    }

    private List<Integer> findNextHikingPositions(Integer currentPosition, int currentNumber, AOC10InputData inputData) {
        int[] coordsFromPosition = getCoordsFromPosition(currentPosition, inputData.fieldWidth());

        return DIRECTIONS.stream()
                .map(direction -> calcNewPosition(coordsFromPosition, direction))
                .filter(p -> isOnField(p[0], p[1], inputData.fieldWidth(), inputData.fieldHeight()))
                .map(c -> getPositionFromCoords(c, inputData.fieldWidth()))
                .filter(p -> inputData.field().get(p).equals(currentNumber))
                .toList();
    }

    @Override
    public AOC10InputData parseInputData(String fileContent) {
        String[] rows = fileContent.split("\n");
        int fieldWidth = rows[0].length();

        List<Integer> field = new ArrayList<>();
        List<Integer> zeroPositions = new ArrayList<>();

        String fieldData = String.join("", rows).trim();

        for (int i = 0; i < fieldData.length(); i++) {
            field.add(Character.getNumericValue(fieldData.charAt(i)));

            if (fieldData.charAt(i) == '0') {
                zeroPositions.add(i);
            }
        }

        return new AOC10InputData(
                fieldWidth,
                rows.length,
                field,
                zeroPositions
        );
    }

    public record AOC10InputData(
            int fieldWidth,
            int fieldHeight,
            List<Integer> field,
            List<Integer> zeroPositions
    ) implements AOCInputData {
    }
}
