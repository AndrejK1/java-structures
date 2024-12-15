package aoc.aoc2024;

import aoc.AOCTask;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static aoc.AOCMatrixUtils.DIRECTIONS;
import static aoc.AOCMatrixUtils.calcNewCoords;
import static aoc.AOCMatrixUtils.getCoordsFromPosition;
import static aoc.AOCMatrixUtils.getPositionFromCoords;
import static aoc.AOCMatrixUtils.isOnField;

public class AOC12GardenGroups implements AOCTask<AOC12GardenGroups.AOC12InputData> {

    @Override
    public String getTaskTitle() {
        return "Day 12: Garden Groups";
    }

    @Override
    public AOCAnswer solve(AOC12InputData inputData) {
        long result1 = 0;
        long result2 = 0;

        Set<Integer> gardenPlotsToCheck = IntStream.range(0, inputData.gardenPlots().length())
                .boxed()
                .collect(Collectors.toSet());

        while (!gardenPlotsToCheck.isEmpty()) {
            result1 += findNewGardenGroupAndCalculateFenceCost(gardenPlotsToCheck, inputData);
        }

        return new AOCAnswer(result1, result2);
    }

    private long findNewGardenGroupAndCalculateFenceCost(Set<Integer> gardenPlotsToCheck, AOC12InputData inputData) {
        Set<Integer> gardenGroupPositions = new HashSet<>();
        long perimeter = 0L;

        Deque<Integer> groupPositionsToCheck = new ArrayDeque<>();
        groupPositionsToCheck.add(gardenPlotsToCheck.iterator().next());

        while (!groupPositionsToCheck.isEmpty()) {
            // pop next position to find enclosing garden group
            Integer positionInGardenGroup = groupPositionsToCheck.pop();
            if (gardenGroupPositions.contains(positionInGardenGroup)) {
                continue;
            }

            // mark position as checked
            gardenPlotsToCheck.remove(positionInGardenGroup);

            // update garden group stats
            perimeter += calculateGroupPerimeterChange(positionInGardenGroup, gardenGroupPositions, inputData);
            gardenGroupPositions.add(positionInGardenGroup);
            // find next
            List<Integer> adjacentGardenGroupPositions = findAdjacentGardenGroupPositions(positionInGardenGroup, gardenPlotsToCheck, inputData);
            adjacentGardenGroupPositions.forEach(groupPositionsToCheck::push);
        }

        return gardenGroupPositions.size() * perimeter;
    }

    private long calculateGroupPerimeterChange(Integer positionInGardenGroup, Set<Integer> gardenGroupPositions, AOC12InputData inputData) {
        int[] coordsFromPosition = getCoordsFromPosition(positionInGardenGroup, inputData.fieldWidth());

        int adjacentGardenGroupTiles = (int) DIRECTIONS.stream()
                .map(direction -> calcNewCoords(coordsFromPosition, direction))
                .filter(p -> isOnField(p[0], p[1], inputData.fieldWidth(), inputData.fieldHeight()))
                .map(c -> getPositionFromCoords(c, inputData.fieldWidth()))
                .filter(gardenGroupPositions::contains)
                .count();

        switch (adjacentGardenGroupTiles) {
            case 0:
                return 4;
            case 1:
                return 2;
            case 2:
                return 0;
            case 3:
                return -2;
            case 4:
                return -4;
        }

        throw new IllegalArgumentException("Incorrect number of adjacent tiles in garden matrix: " + adjacentGardenGroupTiles);
    }

    private List<Integer> findAdjacentGardenGroupPositions(Integer currentPosition, Set<Integer> gardenPlotsToCheck, AOC12InputData inputData) {
        int[] coordsFromPosition = getCoordsFromPosition(currentPosition, inputData.fieldWidth());

        return DIRECTIONS.stream()
                .map(direction -> calcNewCoords(coordsFromPosition, direction))
                .filter(p -> isOnField(p[0], p[1], inputData.fieldWidth(), inputData.fieldHeight()))
                .map(c -> getPositionFromCoords(c, inputData.fieldWidth()))
                .filter(gardenPlotsToCheck::contains)
                .filter(p -> inputData.gardenPlots().charAt(currentPosition) == inputData.gardenPlots().charAt(p))
                .toList();
    }

    @Override
    public AOC12InputData parseInputData(String fileContent) {
        String[] rows = fileContent.split("\n");
        int fieldWidth = rows[0].length();

        return new AOC12InputData(
                fieldWidth,
                rows.length,
                fileContent.replace("\n", "")
        );
    }

    public record AOC12InputData(
            int fieldWidth,
            int fieldHeight,
            String gardenPlots
    ) implements AOCInputData {
    }
}
