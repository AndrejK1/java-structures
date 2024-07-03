package learning.sudoku.algorithm;

import learning.sudoku.SudokuSolver;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;


@NoArgsConstructor
public class ObviousGroupsCheck extends SudokuPositionGroupAlgorithm {

    @Override
    public int getPriority() {
        return 40;
    }

    @Override
    public Difficulty getDifficulty() {
        return Difficulty.INTERMEDIATE;
    }

    @Override
    protected boolean runDeepCheckAtPositions(SudokuSolver sudokuSolver, List<Integer> positionsToCheck) {
        boolean detectedChange = false;

        Map<Integer, List<Integer>> possibleNumbersByPos = positionsToCheck.stream()
                .collect(Collectors.toMap(Function.identity(), sudokuSolver.getPossibleNumbersNotes()::get));

        Map<Integer, Map<Integer, List<Integer>>> positionGroupsBySizeToCheck = possibleNumbersByPos
                .entrySet()
                .stream()
                .filter(posList -> posList.getValue().size() > 1)
                .collect(Collectors.groupingBy(m -> m.getValue().size(), Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

        for (Map.Entry<Integer, Map<Integer, List<Integer>>> positionGroups : positionGroupsBySizeToCheck.entrySet()) {
            Integer groupSize = positionGroups.getKey();
            Map<Integer, List<Integer>> groupByPosition = positionGroups.getValue();

            if (groupSize > groupByPosition.size()) {
                continue;
            }

            Map<List<Integer>, Map<Integer, List<Integer>>> equalSetsWithPositions = groupByPosition.entrySet()
                    .stream()
                    .collect(Collectors.groupingBy(Map.Entry::getValue, Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

            for (Map.Entry<List<Integer>, Map<Integer, List<Integer>>> equalSetsWithPosition : equalSetsWithPositions.entrySet()) {
                List<Integer> valuesSet = equalSetsWithPosition.getKey();
                Set<Integer> setPositions = equalSetsWithPosition.getValue().keySet();

                if (setPositions.size() < groupSize) {
                    continue;
                }

                for (Integer pos : possibleNumbersByPos.keySet()) {
                    if (!setPositions.contains(pos)) {
                        detectedChange = sudokuSolver.getPossibleNumbersNotes().get(pos).removeAll(valuesSet) || detectedChange;
                    }
                }
            }
        }

        return detectedChange;
    }
}