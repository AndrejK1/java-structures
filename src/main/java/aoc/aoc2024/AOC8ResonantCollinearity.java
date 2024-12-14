package aoc.aoc2024;

import aoc.AOCTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static aoc.AOCMatrixUtils.getCoordsFromPosition;
import static aoc.AOCMatrixUtils.getPositionFromCoords;
import static aoc.AOCMatrixUtils.isOnField;

public class AOC8ResonantCollinearity implements AOCTask<AOC8ResonantCollinearity.AOC8InputData> {

    @Override
    public String getTaskTitle() {
        return "Day 8: Resonant Collinearity";
    }

    @Override
    public AOCAnswer solve(AOC8InputData inputData) {
        Map<Character, List<Integer>> antennaPositionsByType = inputData.antennaPositionsByType();

        Set<Integer> sellingPlacesByAntennaType = new HashSet<>();
        Set<Integer> sellingPlacesByAntennaTypeV2 = new HashSet<>();

        for (List<Integer> antennaPositions : antennaPositionsByType.values()) {
            for (int i = 0; i < antennaPositions.size(); i++) {
                int[] antennaCoords = getCoordsFromPosition(antennaPositions.get(i), inputData.fieldWidth());

                for (int j = 0; j < antennaPositions.size(); j++) {
                    if (i == j) {
                        continue;
                    }

                    int[] antenna2Coords = getCoordsFromPosition(antennaPositions.get(j), inputData.fieldWidth());

                    int[] diff = new int[]{antenna2Coords[0] - antennaCoords[0], antenna2Coords[1] - antennaCoords[1]};

                    for (int gridPosition = 1; ; gridPosition++) {
                        int possiblePlaceRow = antenna2Coords[0] + diff[0] * gridPosition;
                        int possiblePlaceColumn = antenna2Coords[1] + diff[1] * gridPosition;

                        if (!isOnField(possiblePlaceRow, possiblePlaceColumn, inputData.fieldWidth(), inputData.fieldHeight())) {
                            break;
                        }

                        int sellingPosition = getPositionFromCoords(possiblePlaceRow, possiblePlaceColumn, inputData.fieldWidth());

                        if (gridPosition == 1) {
                            sellingPlacesByAntennaType.add(sellingPosition);
                        }

                        sellingPlacesByAntennaTypeV2.add(sellingPosition);
                    }
                }
            }
        }

        // adding all antenna poses to second answer
        inputData.antennaPositionsByType().forEach((type, positions) -> {
            if (positions.size() > 1) {
                sellingPlacesByAntennaTypeV2.addAll(positions);
            }
        });

        return new AOCAnswer(
                sellingPlacesByAntennaType.size(),
                sellingPlacesByAntennaTypeV2.size()
        );
    }

    @Override
    public AOC8InputData parseInputData(String fileContent) {
        Map<Character, List<Integer>> antennaPositionsByType = new HashMap<>();

        String[] rows = fileContent.split("\n");
        int fieldWidth = rows[0].length();

        for (int i = 0; i < rows.length; i++) {
            String row = rows[i];

            for (int j = 0; j < row.length(); j++) {
                char character = row.charAt(j);

                if (character != '.') {
                    int position = getPositionFromCoords(i, j, fieldWidth);

                    antennaPositionsByType.compute(character, (ch, pos) -> {
                        if (pos == null) {
                            pos = new ArrayList<>();
                        }

                        pos.add(position);
                        return pos;
                    });
                }
            }
        }

        return new AOC8InputData(
                fieldWidth,
                rows.length,
                antennaPositionsByType);
    }

    public record AOC8InputData(int fieldWidth, int fieldHeight,
                                Map<Character, List<Integer>> antennaPositionsByType) implements AOCInputData {
    }
}
